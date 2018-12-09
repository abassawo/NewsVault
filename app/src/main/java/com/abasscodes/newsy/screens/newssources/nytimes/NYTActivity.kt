package com.abasscodes.newsy.screens.newssources.nytimes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.Toast
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseActivity
import com.abasscodes.newsy.screens.shownewslist.ui.TabAdapter
import com.abasscodes.newsy.settings.SharePreferencesManager
import kotlinx.android.synthetic.main.activity_nyt.*

//toolbar

class NYTActivity : BaseActivity() {
  lateinit var adapter: TabAdapter

  override val layoutResourceId: Int get() = R.layout.activity_nyt

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    adapter = TabAdapter(getSupportFragmentManager())
    toolbar.setTitle(R.string.nyt_title)
    toolbar.setOnClickListener({
      SharePreferencesManager.getInstance(this).toggleAdminPriv()
      val toastMsg = "Debug build " + SharePreferencesManager.getInstance(this).hasAdminPriv()
      Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
    })
    setSupportActionBar(toolbar)
    getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
    viewpager.setAdapter(adapter)
    tabs.setupWithViewPager(viewpager)
    tabs.setTabMode(TabLayout.MODE_SCROLLABLE)
  }

  companion object {

    fun makeIntent(context: Context): Intent {
      return Intent(context, NYTActivity::class.java)
    }
  }
}
