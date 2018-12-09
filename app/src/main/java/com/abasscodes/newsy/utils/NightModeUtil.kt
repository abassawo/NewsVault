package com.abasscodes.newsy.utils

import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatDelegate
import com.abasscodes.newsy.settings.SharePreferencesManager

object NightModeUtil {

  fun setNightModeAndRestartActivity(activity: FragmentActivity, mode: Int) {
    setNightModeNoRestartActivity(activity, mode)
    activity.startActivity(activity.intent)
  }

  fun setNightModeNoRestartActivity(activity: FragmentActivity, mode: Int) {
    AppCompatDelegate.setDefaultNightMode(mode)
    val sharePreferencesManager = SharePreferencesManager.getInstance(activity) as SharePreferencesManager
    sharePreferencesManager.setIsNightModeEnabled(mode)
  }

  fun setCorrectDayNightMode(activity: FragmentActivity) {
    val sharePreferencesManager = SharePreferencesManager.getInstance(activity) as SharePreferencesManager
    val nightMode = sharePreferencesManager.getNightMode()
    setNightModeNoRestartActivity(activity, nightMode)
  }

  fun resumeCorrectDayNight(activity: FragmentActivity) {
    setCorrectDayNightMode(activity)
  }
}
