package com.abasscodes.newsy.screens.newssources.newsapi

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.abasscodes.newsy.base.BaseNewsFragment
import com.abasscodes.newsy.models.NewsApiResponse
import com.abasscodes.newsy.models.NewsApiResponse.Article
import com.abasscodes.newsy.viewmodels.NewsApiViewModel
import kotlinx.android.synthetic.main.news_fragment.recyclerView
import kotlinx.android.synthetic.main.news_fragment.swipeRefreshLayout

class WsjFragment : BaseNewsFragment<NewsApiArticleAdapter>() {
  private var viewModel: NewsApiViewModel? = null

  override fun createAdapter(): NewsApiArticleAdapter {
    return NewsApiArticleAdapter()
  }

  override fun subscribe() {
    viewModel = ViewModelProviders.of(this).get(NewsApiViewModel::class.java)
    val bundle = arguments
    val newsSource = bundle!!.getSerializable(NEWS_API_SOURCE) as NewsApiSource
    viewModel!!.getAllArticles(newsSource.source).observe(this, Observer<List<Article>> { this.showArticles(it) })
  }

  fun showArticles(articles: List<NewsApiResponse.Article>?) {
    swipeRefreshLayout.setRefreshing(false)
    if (articles == null || articles.isEmpty()) {
      showError()
    } else {
      adapter.setItems(articles)
      if (recyclerView.getAdapter() == null) {
        recyclerView.setAdapter(adapter)
      }
    }
  }

  companion object {
    private val NEWS_API_SOURCE = "NEWS_SOURCE"
    val NEWS_API_TITLE = "NEWS_API_TITLE"

    fun newInstance(source: NewsApiSource): WsjFragment {
      val fragment = WsjFragment()
      val bundle = Bundle()
      bundle.putSerializable(NEWS_API_SOURCE, source)
      fragment.arguments = bundle
      return fragment
    }
  }
}
