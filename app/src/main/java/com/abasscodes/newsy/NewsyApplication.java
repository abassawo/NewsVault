package com.abasscodes.newsy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.abasscodes.newsy.data.rest.RetrofitClient;
import com.facebook.stetho.Stetho;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;

public class NewsyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private Context currentActivity;
    public static Picasso picassoWithCache;
    private static Cache cache;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);

        //INit Cache
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        cache = new Cache(httpCacheDirectory, 15 * 1024 * 1024);
    }

    public static Cache getCache() {
        return cache;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.currentActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
