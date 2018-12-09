package com.abasscodes.newsy.screens.mainscreen.dashboard;

import android.content.Context;

import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiSource;
import com.abasscodes.newsy.screens.newssources.nytimes.NytDashboard;
import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiDashboard;
import com.abasscodes.newsy.settings.SharePreferencesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DashboardCardViewFactory {

    private static boolean showNyTimes = false; //todo - replace with Feature Flag/Rollout

    public static List<DashboardView> getAllCards(Context context) {
        List<DashboardView> dashboardViews = new ArrayList<>();
        dashboardViews.add(new NytDashboard(context));
        for(NewsApiSource source : NewsApiSource.values()) {
            dashboardViews.add(NewsApiDashboard.Companion.ofSource(context, source));
        }
        return dashboardViews;
    }

    public static List<DashboardView> getPermittedCards(Context context) {
        List<DashboardView> dashboards = new ArrayList<>();
        if(showNyTimes) {
            dashboards.add(new NytDashboard(context));
        }
        for(NewsApiSource source : NewsApiSource.values()) {
            int id = source.ordinal();
            if(SharePreferencesManager.getInstance(context).isDashboardPermitted(id)) {
                dashboards.add(NewsApiDashboard.Companion.ofSource(context, source));
            }
        }
        return dashboards;
    }
}
