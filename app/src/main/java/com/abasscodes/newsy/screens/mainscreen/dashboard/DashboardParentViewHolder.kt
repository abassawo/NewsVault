package com.abasscodes.newsy.screens.mainscreen.dashboard

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.abasscodes.newsy.R
import com.abasscodes.newsy.utils.dragandrop.ItemTouchHelperViewHolder
import kotlinx.android.synthetic.main.dashboard_card_parent.view.controlBtns
import kotlinx.android.synthetic.main.dashboard_card_parent.view.frameLayout

class DashboardParentViewHolder(view: View) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder, View.OnClickListener {
  private var view: DashboardView? = null

  fun bind(view: DashboardView) {
    this.view = view

    itemView.frameLayout.removeAllViews()
    if (view.parent != null) {
      (view.parent as ViewGroup).removeAllViews()
    }
      itemView.frameLayout.addView(view)
      itemView.frameLayout.requestLayout()

    this.view!!.refresh()
    for (i in 0 until itemView.controlBtns.getChildCount()) {
      itemView.controlBtns.getChildAt(i).setOnClickListener(this)
    }
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.backBtn -> view!!.onDecrementClick()
      R.id.nextBtn -> view!!.onIncrementClick()
      R.id.view_more -> view!!.onViewAllClicked()
    }
  }

  override fun onItemSelected() {

  }

  override fun onItemClear() {

  }

  init {
  }

  companion object {
    val LAYOUT_ID = R.layout.dashboard_card_parent
  }
}
