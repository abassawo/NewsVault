package com.abasscodes.newsy.screens.newssources.nytimes

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.abasscodes.newsy.R
import com.abasscodes.newsy.models.NytResponse
import com.abasscodes.newsy.screens.mainscreen.dashboard.DashboardView
import com.abasscodes.newsy.screens.shownewslist.ui.NytArticleAdapter
import com.abasscodes.newsy.utils.ContentUtil
import com.abasscodes.newsy.viewmodels.NytArticlesViewModel
import kotlinx.android.synthetic.main.dashboard_view.view.recyclerView
import kotlinx.android.synthetic.main.dashboard_view.view.toolbar

class NytDashboard(context: Context) : DashboardView(context) {
  private var viewModel: NytArticlesViewModel? = null
  private val articleAdapter = NytArticleAdapter()

  override var title: String
    get() = "Ny Times"
    set(value) {
      super.title = value
    }

  init {
    view.toolbar.setTitle("NY Times")
    toolbar.setOnMenuItemClickListener({ item ->
      when (item.itemId) {
        R.id.save_article_option -> {
          val result = articleAdapter.results[layoutManager.findFirstVisibleItemPosition()]
          ContentUtil.saveResult(getContext(), result)
        }
      }
      false
    })
    view.recyclerView.setAdapter(articleAdapter)
    refresh()
  }

  private fun showResults(results: List<NytResponse.Result>?) {
    if (results == null || results.isEmpty()) {
      //            showError();
    } else {
      articleAdapter.results = results
    }
  }

  override fun onViewAllClicked() {
    context.startActivity(NYTActivity.makeIntent(context))
  }

  override fun refresh() {
    viewModel = ViewModelProviders.of(context as FragmentActivity).get(NytArticlesViewModel::class.java)
    viewModel!!.results.observe(context as LifecycleOwner, Observer { showResults(it) })
    viewModel!!.getResults("home")
  }
}
