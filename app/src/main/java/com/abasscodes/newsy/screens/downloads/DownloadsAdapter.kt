package com.abasscodes.newsy.screens.downloads

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseViewHolder
import com.abasscodes.newsy.data.database.SerializableArticle
import com.abasscodes.newsy.screens.shownewsdetail.NewsWebViewActivity
import com.abasscodes.newsy.utils.PicassoUtil
import kotlinx.android.synthetic.main.download_item_view_holder.view.media
import kotlinx.android.synthetic.main.download_item_view_holder.view.title
import java.util.ArrayList

internal class DownloadsAdapter(items: HashSet<SerializableArticle>) : RecyclerView.Adapter<DownloadsAdapter.DownloadsViewHolder>() {
  private val items = HashSet<SerializableArticle>()

  init {
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(
        DownloadsViewHolder.LAYOUT_ID, parent, false
      )
    return DownloadsViewHolder(view)
  }

  override fun onBindViewHolder(holder: DownloadsViewHolder, position: Int) {
    holder.bind(items.elementAt(position))
  }

  override fun getItemCount(): Int {
    return items.size
  }

  class DownloadsViewHolder(view: View) : BaseViewHolder<SerializableArticle>(view) {

    override fun bind(item: SerializableArticle) {
      super.bind(item)
      itemView.title.setText(item.title)
      PicassoUtil.setImage(itemView.media, item.imageUrl)
      itemView.setOnClickListener {
        val context = itemView.context
        context.startActivity(NewsWebViewActivity.newIntent(context, item.url))
      }
    }

    companion object {

      var LAYOUT_ID = R.layout.download_item_view_holder
    }
  }
}
