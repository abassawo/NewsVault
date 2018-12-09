package com.abasscodes.newsy.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

public final class SharePreferencesManager implements UserSettings {

  private static final String LAST_QUERY = "last_query";
  private static final String ADMIN_PRIV = "admin_priv";
  private static final String IS_FIRST_RUN = "first_run";
  private static final String IS_NIGHT_MODE = "night_mode_on";
  private static final String NIGHT_MODE_AUTO_DEVICE = "night_mode_inherit_auto_from_device";

  private static SharePreferencesManager instance;

  private final SharedPreferences preferences;

  public synchronized static UserSettings getInstance(Context context) {
    if (instance == null) {
      instance = new SharePreferencesManager(context);
    }
    return instance;
  }

  private SharePreferencesManager(Context context) {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Override
  public void setLastQuery(String timeQuery) {
    preferences.edit()
      .putString(LAST_QUERY, timeQuery)
      .apply();
  }

  @Override
  public boolean hasAdminPriv() {
    return preferences.getBoolean(ADMIN_PRIV, false);
  }

  @Override
  public void setAdminPriv(boolean priv) {
    preferences.edit()
      .putBoolean(ADMIN_PRIV, priv)
      .commit();
  }

  @Override
  public void toggleAdminPriv() {
    boolean hasPriv = hasAdminPriv();
    setAdminPriv(!hasPriv);
  }

  @Override
  public boolean isDashboardPermitted(int id) {
    return preferences.getBoolean(String.valueOf(id), true);
  }

  @Override
  public void saveDashboardPermission(int id, boolean permit) {
    preferences.edit()
      .putBoolean(String.valueOf(id), permit)
      .commit();
  }

  @Override
  public boolean isFirstRun() {
    return preferences.getBoolean(IS_FIRST_RUN, true);
  }

  @Override
  public void setFirstRun(boolean isFirstRun) {
    preferences.edit()
      .putBoolean(IS_FIRST_RUN, isFirstRun)
      .commit();
  }

  @Override
  public boolean isNightModeEnabled() {
    return getNightMode() == MODE_NIGHT_YES;
  }

  @Override
  public int getNightMode() {
    return preferences.getInt(IS_NIGHT_MODE, MODE_NIGHT_NO);
  }

  @Override
  public void setIsNightModeEnabled(int nightMode) {
    preferences.edit()
      .putInt(IS_NIGHT_MODE, nightMode)
      .commit();
  }

  @Override
  @Nullable
  public String getLastQuery() {
    return preferences.getString(LAST_QUERY, null);
  }
}
