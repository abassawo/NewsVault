package com.abasscodes.newsy.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.abasscodes.newsy.NewsyApplication
import com.abasscodes.newsy.settings.SharePreferencesManager
import com.abasscodes.newsy.utils.NightModeUtil

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

  protected abstract val layoutResourceId: Int

  protected val newsyApplication: NewsyApplication
    get() = application as NewsyApplication

  override fun onCreate(savedInstanceState: Bundle?) {
//    NightModeUtil.setCorrectDayNightMode(this)
    super.onCreate(savedInstanceState)
    setContentView(layoutResourceId)
  }

  override fun onResume() {
    super.onResume()
    NightModeUtil.resumeCorrectDayNight(this)
  }

  override fun showError() {
    Toast.makeText(this, "Error", Toast.LENGTH_LONG)
      .show()
  }
}