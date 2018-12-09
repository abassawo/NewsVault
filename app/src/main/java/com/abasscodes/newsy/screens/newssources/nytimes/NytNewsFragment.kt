package com.abasscodes.newsy.screens.newssources.nytimes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.abasscodes.newsy.base.BaseNewsFragment
import com.abasscodes.newsy.models.NytResponse
import com.abasscodes.newsy.screens.shownewslist.ui.NytArticleAdapter
import com.abasscodes.newsy.viewmodels.NytArticlesViewModel
import kotlinx.android.synthetic.main.news_fragment.recyclerView
import kotlinx.android.synthetic.main.news_fragment.swipeRefreshLayout

class NytNewsFragment : BaseNewsFragment<NytArticleAdapter>() {
  private var resultsViewModel: NytArticlesViewModel? = null

  override fun createAdapter(): NytArticleAdapter {
    return NytArticleAdapter()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    resultsViewModel = ViewModelProviders.of(this).get(NytArticlesViewModel::class.java)
    resultsViewModel!!.results.observe(this, Observer { showResults(it) })
  }

  override fun subscribe() {
    val section = arguments!!.getString(EXTRA_SECTION)
    resultsViewModel!!.getResults(section)

  }

  override fun onResume() {
    super.onResume()
    subscribe()
  }

  fun showResults(results: List<NytResponse.Result>?) {
    swipeRefreshLayout.setRefreshing(false)
    if (results == null || results.isEmpty()) {
      showError()
    } else {
      adapter.results = results
      if (recyclerView.getAdapter() == null) {
        recyclerView.setAdapter(adapter)
      }
    }
  }

  companion object {
    private val EXTRA_SECTION = "extraSection"

    fun newInstance(section: String): NytNewsFragment {
      val args = Bundle()
      val fragment = NytNewsFragment()
      args.putString(EXTRA_SECTION, section)
      fragment.arguments = args
      return fragment
    }
  }
}