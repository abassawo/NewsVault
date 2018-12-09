package com.abasscodes.newsy.screens.newssources.newsapi

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import com.abasscodes.newsy.R
import com.abasscodes.newsy.models.NewsApiResponse.Article
import com.abasscodes.newsy.screens.mainscreen.dashboard.DashboardView
import com.abasscodes.newsy.utils.ContentUtil
import com.abasscodes.newsy.viewmodels.NewsApiViewModel
import kotlinx.android.synthetic.main.dashboard_view.view.*

class NewsApiDashboard(context: Context, private val source: NewsApiSource) : DashboardView(context) {
  private var viewModel: NewsApiViewModel? = null
  private val articleAdapter = NewsApiArticleAdapter()

  init {
    view.toolbar.setTitle(source.getName())
    toolbar.setOnMenuItemClickListener({ item ->
      when (item.itemId) {
        R.id.save_article_option -> {
          val article = articleAdapter.articles[layoutManager.findFirstVisibleItemPosition()]
          ContentUtil.saveArticle(getContext(), article)
        }
      }
      false
    })
    recyclerView.setAdapter(articleAdapter)
  }

  private fun showArticles(map: Map<String, List<Article>>?) {
    if (map == null) return
    val sourceText = source.source ?: return
    val results = map[sourceText]
    results?.forEach { Log.d(TAG + source.getName(), it.url) }
    articleAdapter.setItems(results)
  }

  override fun onViewAllClicked() {
    context.startActivity(WsjActivity.makeIntent(context, source))
  }

  override fun refresh() {
    if (source != null) {
      viewModel = ViewModelProviders.of(context as FragmentActivity).get(NewsApiViewModel::class.java)
      Log.d(TAG, "Showing $source")
      viewModel!!.getTopArticles(source.source)
        .observe(context as LifecycleOwner, Observer<Map<String, List<Article>>> { showArticles(it) })
    }
  }

  companion object {
    val NEWS_API_TITLE = "NEWS_API_TITLE"
    private val TAG = NewsApiDashboard::class.java.simpleName
    fun ofSource(context: Context, source: NewsApiSource): NewsApiDashboard {
      return NewsApiDashboard(context, source)
    }
  }
}
