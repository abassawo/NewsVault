package com.abasscodes.newsy.settings.customizedashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseActivity
import com.abasscodes.newsy.screens.mainscreen.MainActivity
import com.abasscodes.newsy.settings.SharePreferencesManager
import kotlinx.android.synthetic.main.activity_main.customizeDashboardButton
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.swipeRefresh
import kotlinx.android.synthetic.main.activity_main.toolbar

class CustomizeSettingsActivity : BaseActivity() {
  private var adapter: CustomizeDashboardAdapter? = null

  override val layoutResourceId: Int get() = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    adapter = CustomizeDashboardAdapter()
    SharePreferencesManager.getInstance(this).isFirstRun = false
    toolbar.setOnClickListener({
      SharePreferencesManager.getInstance(this).toggleAdminPriv()
      val toastMsg = "Debug build " + SharePreferencesManager.getInstance(this).hasAdminPriv()
      Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
    })
    swipeRefresh.setEnabled(false)
    customizeDashboardButton.setText("Done")
    customizeDashboardButton.setOnClickListener({ startActivity(MainActivity.makeIntent(this)) })
    recyclerView.setLayoutManager(LinearLayoutManager(this))
    recyclerView.setAdapter(adapter)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_customize, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.customize_done -> startActivity(MainActivity.makeIntent(this))
      R.id.customize_select_all -> adapter!!.selectAll(this)
      R.id.customize_unselect_all -> adapter!!.unselectAll(this)
    }
    return true
  }

  companion object {

    fun makeIntent(context: Context): Intent {
      return Intent(context, CustomizeSettingsActivity::class.java)
    }
  }
}