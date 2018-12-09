package com.abasscodes.newsy.base

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.abasscodes.newsy.R
import com.abasscodes.newsy.utils.NightModeUtil
import kotlinx.android.synthetic.main.news_fragment.recyclerView
import kotlinx.android.synthetic.main.news_fragment.swipeRefreshLayout

/**
 * Created by zkmvf1n on 5/26/18.
 */

abstract class BaseNewsFragment<T : RecyclerView.Adapter<*>> : BaseFragment() {
  lateinit var adapter: T
  var isGrid: Boolean = false

  override fun getLayoutResourceId(): Int {
    return R.layout.news_fragment
  }

  override fun onResume() {
    super.onResume()
    subscribe()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater!!.inflate(R.menu.menu_news_list, menu)
    menu?.findItem(R.id.menu_item_toggle)?.icon =
        if (isGrid) ContextCompat.getDrawable(context!!, R.drawable.ic_table_large)
        else ContextCompat.getDrawable(context!!, R.drawable.ic_format_list_bulleted)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
    swipeRefreshLayout.setOnRefreshListener({ subscribe() })
  }

  override fun onPrepareOptionsMenu(menu: Menu?) {
    val layoutManager = recyclerView.getLayoutManager()
    isGrid = if (layoutManager != null && layoutManager is GridLayoutManager) true else false
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    val activity = activity as AppCompatActivity
    when (item!!.itemId) {
      android.R.id.home -> {
        activity.onBackPressed()
      }
      R.id.menu_item_toggle -> {
        //todo - persist the changes to this value so it works for the other tabs as well.
        val layoutManager = recyclerView.getLayoutManager()
        if (layoutManager is GridLayoutManager) {
          recyclerView.setLayoutManager(LinearLayoutManager(context))
        } else {
          recyclerView.setLayoutManager(GridLayoutManager(context, 2))
        }
        activity.invalidateOptionsMenu()
      }
      R.id.menu_night_mode_system -> NightModeUtil.setNightModeAndRestartActivity(activity, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
      R.id.menu_night_mode_day -> NightModeUtil.setNightModeAndRestartActivity(activity, AppCompatDelegate.MODE_NIGHT_NO)
      R.id.menu_night_mode_night -> NightModeUtil.setNightModeAndRestartActivity(activity, AppCompatDelegate.MODE_NIGHT_YES)
      R.id.menu_night_mode_auto -> NightModeUtil.setNightModeAndRestartActivity(activity, AppCompatDelegate.MODE_NIGHT_AUTO)
    }
    return true
  }

  private fun setupRecyclerView() {
    adapter = createAdapter()
    recyclerView.setLayoutManager(LinearLayoutManager(context))
    recyclerView.setAdapter(adapter)
    recyclerView.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
  }

  protected abstract fun createAdapter(): T

  protected abstract fun subscribe()
}
