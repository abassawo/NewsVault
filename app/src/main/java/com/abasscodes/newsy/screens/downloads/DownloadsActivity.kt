package com.abasscodes.newsy.screens.downloads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseActivity
import com.abasscodes.newsy.data.database.DatabaseHelper
import com.abasscodes.newsy.data.database.Serializer
import kotlinx.android.synthetic.main.activity_main.customizeDashboardButton
import kotlinx.android.synthetic.main.activity_main.*

class DownloadsActivity : BaseActivity() {

  private var adapter: DownloadsAdapter? = null

  override val layoutResourceId: Int get() = R.layout.activity_main

  protected override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val db = DatabaseHelper(this).writableDatabase
    val serializer = Serializer.getInstance(db)
    val mItems = serializer.faves
    adapter = DownloadsAdapter(mItems)
    setSupportActionBar(toolbar)
    supportActionBar!!.setTitle(getString(R.string.app_name) + ": Saved items")
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    customizeDashboardButton.setVisibility(View.GONE)
    recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    recyclerView.setLayoutManager(LinearLayoutManager(this))
    recyclerView.setAdapter(adapter)
    swipeRefresh.setEnabled(false)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return true
  }

  companion object {

    fun newIntent(context: Context): Intent {
      return Intent(context, DownloadsActivity::class.java)
    }
  }
}
