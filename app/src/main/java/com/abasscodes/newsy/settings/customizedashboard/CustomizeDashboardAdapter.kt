package com.abasscodes.newsy.settings.customizedashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abasscodes.newsy.R
import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiSource
import com.abasscodes.newsy.settings.SharePreferencesManager
import com.abasscodes.newsy.utils.dragandrop.ItemTouchHelperViewHolder
import kotlinx.android.synthetic.main.customize_dashboard_viewholder_item.view.checkbox
import kotlinx.android.synthetic.main.customize_dashboard_viewholder_item.view.settingsNewsTitle
import java.util.ArrayList
import java.util.Arrays

internal class CustomizeDashboardAdapter : RecyclerView.Adapter<CustomizeDashboardAdapter.CustomizeDashboardViewHolder>() {
  private val dashboardViews: List<NewsApiSource>

  init {
    dashboardViews = ArrayList(Arrays.asList(*NewsApiSource.values()))
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomizeDashboardViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = layoutInflater.inflate(CustomizeDashboardViewHolder.LAYOUT_ID, parent, false)
    return CustomizeDashboardViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CustomizeDashboardViewHolder, position: Int) {
    holder.bind(dashboardViews[position])
  }

  override fun getItemCount(): Int {
    return dashboardViews.size
  }

  fun selectAll(context: Context) {
    for (source in NewsApiSource.values()) {
      SharePreferencesManager.getInstance(context).saveDashboardPermission(source.ordinal, true)
    }
    notifyDataSetChanged()
  }

  fun unselectAll(context: Context) {
    for (source in NewsApiSource.values()) {
      SharePreferencesManager.getInstance(context).saveDashboardPermission(source.ordinal, false)
    }
    notifyDataSetChanged()
  }

  class CustomizeDashboardViewHolder(view: View) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {

    override fun onItemSelected() {

    }

    override fun onItemClear() {

    }

    fun bind(source: NewsApiSource) {
      itemView.settingsNewsTitle.text = source.getName()
      val id = source.ordinal
      val context = itemView.context
      itemView.checkbox.setChecked(SharePreferencesManager.getInstance(context).isDashboardPermitted(id))


      itemView.checkbox.setOnCheckedChangeListener({ _, isChecked ->
        SharePreferencesManager.getInstance(context).saveDashboardPermission(source.ordinal, isChecked)
      })

    }

    companion object {

      val LAYOUT_ID = R.layout.customize_dashboard_viewholder_item
    }
  }
}
