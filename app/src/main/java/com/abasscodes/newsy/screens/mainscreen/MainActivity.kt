package com.abasscodes.newsy.screens.mainscreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseActivity
import com.abasscodes.newsy.screens.downloads.DownloadsActivity
import com.abasscodes.newsy.screens.mainscreen.dashboard.DashboardAdapter
import com.abasscodes.newsy.screens.mainscreen.dashboard.DashboardView
import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiSource
import com.abasscodes.newsy.screens.shownewsdetail.NewsWebViewActivity
import com.abasscodes.newsy.settings.SharePreferencesManager
import com.abasscodes.newsy.settings.customizedashboard.CustomizeSettingsActivity
import com.abasscodes.newsy.utils.NightModeUtil
import com.abasscodes.newsy.utils.dragandrop.SimpleItemTouchHelperCallback
import com.abasscodes.newsy.viewmodels.DashboardCardViewModel
import kotlinx.android.synthetic.main.activity_main.customizeDashboardButton
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.navView
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.swipeRefresh
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : BaseActivity(), View.OnClickListener {
  lateinit var viewModel: DashboardCardViewModel
  private val adapter = DashboardAdapter()

  override val layoutResourceId: Int get()=  R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupRecyclerview(recyclerView)
    viewModel = ViewModelProviders.of(this).get(DashboardCardViewModel::class.java)
    viewModel.getDashboardViews(this).observe(this, Observer<List<DashboardView>> { updateUI(it) })
    setupToolbar(toolbar)
    setupNavView(navView)
    toolbar.setClickable(true)
    toolbar.setOnLongClickListener {
      SharePreferencesManager.getInstance(this).toggleAdminPriv()
      val toastMsg = "Debug build " + SharePreferencesManager.getInstance(this).hasAdminPriv()
      Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
      false
    }

    toolbar.setOnClickListener(this)
    customizeDashboardButton.setOnClickListener(this)

    swipeRefresh.setOnRefreshListener({ refresh() })
  }

  override fun onResume() {
    super.onResume()
    refresh()
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  private fun refresh() {
    if(getSupportFragmentManager().isDestroyed()) {
      return;
    }
    viewModel.getDashboardViews(this)
    swipeRefresh.setRefreshing(false)
  }

  private fun updateUI(dashboardViews: List<DashboardView>?) {
    if (dashboardViews == null) return
    recyclerView.setAdapter(null)
    if (adapter.items !== dashboardViews) {
      adapter.items = dashboardViews
      if (recyclerView.getAdapter() == null) {
        recyclerView.setAdapter(adapter)
      }
      recyclerView.scrollToPosition(dashboardViews.size - 1)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    getMenuInflater().inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> openDrawer()
      R.id.menu_item_saved -> startActivity(Intent(DownloadsActivity.newIntent(this)))
      R.id.menu_night_mode_system -> NightModeUtil.setNightModeAndRestartActivity(this, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
      R.id.menu_night_mode_day -> NightModeUtil.setNightModeAndRestartActivity(this, AppCompatDelegate.MODE_NIGHT_NO)
      R.id.menu_night_mode_night -> NightModeUtil.setNightModeAndRestartActivity(this, AppCompatDelegate.MODE_NIGHT_YES)
      R.id.menu_night_mode_auto -> NightModeUtil.setNightModeAndRestartActivity(this, AppCompatDelegate.MODE_NIGHT_AUTO)
    }
    return true
  }

  private fun setupNavView(navigationView: NavigationView) {
    navigationView.itemTextColor = ColorStateList.valueOf(Color.WHITE)
    val menu = navigationView.menu.findItem(R.id.subscriptions)
    for (source in NewsApiSource.values()) {
      val id = source.ordinal
      if (SharePreferencesManager.getInstance(this).isDashboardPermitted(id)) {
        menu.subMenu.add(source.toString() + " Mobile Site")
      }
    }
    navigationView.setNavigationItemSelectedListener { item ->
      item.isChecked = true
      drawerLayout.closeDrawers()
      false
    }


    navigationView.setNavigationItemSelectedListener { item ->
      if (item.title.toString().contains("Mobile Site")) {
        Log.d("Main Act. Menu ", item.title.toString())
      }
      when (item.itemId) {
        R.id.nyt_mobile_menu -> startActivity(Intent(NewsWebViewActivity.newIntent(this, "https://www.nytimes.com")))
        //                case R.id.wsj_mobile_site:
        //                    startActivity(new Intent(NewsWebViewActivity.newIntent(MainActivity.this, "https://www.wsj.com")));
        //                    break;
        R.id.manage_subscriptions -> startActivity(Intent(this, CustomizeSettingsActivity::class.java))
      }
      true
    }
  }

  fun openDrawer() {
    drawerLayout.openDrawer(GravityCompat.START)
  }

  fun setupRecyclerview(recyclerview: RecyclerView) {
    recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
          val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
          val position = layoutManager.findLastCompletelyVisibleItemPosition()
          val view = layoutManager.findViewByPosition(position)
          view?.requestFocus()
        }
      }
    })
    recyclerview.setItemViewCacheSize(3)
    val layoutManager = LinearLayoutManager(this)
    recyclerview.layoutManager = layoutManager
    layoutManager.initialPrefetchItemCount = 4
    recyclerview.adapter = adapter
    recyclerview.setHasFixedSize(true)
    val callback = SimpleItemTouchHelperCallback(adapter)
    val touchHelper = ItemTouchHelper(callback)
    touchHelper.attachToRecyclerView(recyclerview)
  }

  fun setupToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    getSupportActionBar()!!.setHomeAsUpIndicator(R.drawable.ic_menu)
    getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.toolbar -> {
        SharePreferencesManager.getInstance(this).toggleAdminPriv()
        val toastMsg = "Debug build " + SharePreferencesManager.getInstance(this).hasAdminPriv()
        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
      }
      R.id.customizeDashboardButton -> startActivity(CustomizeSettingsActivity.makeIntent(this))
    }
  }

  companion object {

    fun makeIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }
}
