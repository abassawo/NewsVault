package com.abasscodes.newsy.settings;

import android.content.Context;
import android.support.annotation.Nullable;

public interface UserSettings {

    void setLastQuery(String query);

    @Nullable
    String getLastQuery();

    boolean hasAdminPriv();

    void setAdminPriv(boolean priv);

    void toggleAdminPriv();

    boolean isDashboardPermitted(int id);

    void saveDashboardPermission(int id, boolean permit);

    boolean isFirstRun();

    void setFirstRun(boolean isFirstRun);

    boolean isNightModeEnabled();

    int getNightMode();

    void setIsNightModeEnabled(int nightMode);
}
