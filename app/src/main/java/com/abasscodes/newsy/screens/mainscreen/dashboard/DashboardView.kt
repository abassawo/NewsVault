package com.abasscodes.newsy.screens.mainscreen.dashboard

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.abasscodes.newsy.R
import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiSource
import kotlinx.android.synthetic.main.dashboard_view.view.*

abstract class DashboardView(context: Context) : FrameLayout(context) {
  private val snapHelper = PagerSnapHelper()
  var layoutManager: LinearLayoutManager
  private val source: NewsApiSource? = null
  var view: View

  val recyclerPosition: Int
    get() = layoutManager.findLastCompletelyVisibleItemPosition()

  open var title: String = "default"
    get() = if (source == null) "default" else source.getName()

  init {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    view = inflater.inflate(R.layout.dashboard_view, this, false)
    addView(view)
    layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
    layoutManager.initialPrefetchItemCount = 4
    view.recyclerView.setLayoutManager(layoutManager)
    view.recyclerView.setHasFixedSize(true)

    view.toolbar.inflateMenu(R.menu.menu_dashboard)
    snapHelper.attachToRecyclerView(recyclerView)
  }

  fun onDecrementClick() {
    val position = recyclerPosition
    if (position > 0) {
      view.recyclerView.smoothScrollToPosition(position - 1)
    }
  }

  fun onIncrementClick() {
    val position = recyclerPosition
    if (position < view.recyclerView.getAdapter().getItemCount()) {
      view.recyclerView.smoothScrollToPosition(position + 1)
    }
  }

  abstract fun onViewAllClicked()

  abstract fun refresh()
}
