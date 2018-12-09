package com.abasscodes.newsy.screens.shownewslist.ui

import android.app.AlertDialog
import android.view.View
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseViewHolder
import com.abasscodes.newsy.models.NytResponse
import com.abasscodes.newsy.screens.shownewsdetail.NewsWebViewActivity
import com.abasscodes.newsy.utils.ContentUtil
import com.abasscodes.newsy.utils.PicassoUtil
import kotlinx.android.synthetic.main.article_view_holder.view.media
import kotlinx.android.synthetic.main.article_view_holder.view.section
import kotlinx.android.synthetic.main.article_view_holder.view.title

class ArticleViewHolder(view: View) : BaseViewHolder<NytResponse.Result>(view), View.OnClickListener, View.OnLongClickListener {

  override fun bind(item: NytResponse.Result) {
    super.bind(item)
    itemView.requestFocus()
    itemView.title.setText(item.title)
    itemView.section.setText(item.section)
    itemView.setOnClickListener(this)
    PicassoUtil.setImage(itemView.media, item.bestImagePath)
  }

  override fun onClick(view: View) {
    showArticleInApp()
  }

  private fun showArticleInApp() {
    val intent = NewsWebViewActivity.newIntent(context, item)
    context.startActivity(intent)
  }

  override fun onLongClick(v: View): Boolean {
    if (v === itemView) {
      val builder = AlertDialog.Builder(v.context)
      builder.setTitle(R.string.dialog_save_prompt)
      builder.setPositiveButton("Yes") { dialog, which -> ContentUtil.saveResult(v.context, item) }
      builder.setNegativeButton("Cancel") { dialog, which ->

      }

      builder.show()
    }
    return true
  }

  companion object {

    val layoutid = R.layout.article_view_holder
  }
}