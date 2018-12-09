package com.abasscodes.newsy.screens.newssources.newsapi

import android.app.AlertDialog
import android.view.View
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseViewHolder
import com.abasscodes.newsy.models.NewsApiResponse
import com.abasscodes.newsy.screens.shownewsdetail.NewsWebViewActivity
import com.abasscodes.newsy.screens.shownewslist.ui.ArticleViewHolder
import com.abasscodes.newsy.utils.ContentUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_view_holder.view.media
import kotlinx.android.synthetic.main.article_view_holder.view.title

class WsjViewHolder(view: View) : BaseViewHolder<NewsApiResponse.Article>(view), View.OnLongClickListener {

  override fun bind(item: NewsApiResponse.Article) {
    super.bind(item)
    itemView.title.text = item.title
    Picasso.with(context).load(item.urlToImage)
      .placeholder(R.drawable.news_placeholder)
      .into(itemView.media)
    itemView.setOnClickListener { view ->
      val intent = NewsWebViewActivity.newIntent(context, item)
      context.startActivity(intent)
    }
  }

  override fun onLongClick(v: View): Boolean {
    if (v === itemView) {
      val builder = AlertDialog.Builder(v.context)
      builder.setTitle(R.string.dialog_save_prompt)
      builder.setPositiveButton("Yes") { dialog, which -> ContentUtil.saveArticle(v.context, item) }
      builder.setNegativeButton("Cancel") { dialog, which -> dialog.dismiss()}

      builder.show()
    }
    return true
  }

  companion object {
    val layoutId = ArticleViewHolder.layoutid
  }
}
