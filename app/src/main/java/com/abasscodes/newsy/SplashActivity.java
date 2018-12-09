package com.abasscodes.newsy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abasscodes.newsy.screens.mainscreen.MainActivity;
import com.abasscodes.newsy.settings.SharePreferencesManager;
import com.abasscodes.newsy.settings.customizedashboard.CustomizeSettingsActivity;

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharePreferencesManager.getInstance(this).isFirstRun()) {
            startActivity(CustomizeSettingsActivity.Companion.makeIntent(this));
        } else {
            startActivity(MainActivity.Companion.makeIntent(this));
        }
        finish();
    }
}
