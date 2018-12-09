package com.abasscodes.newsy.screens.newssources.nytimes;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.abasscodes.newsy.screens.newssources.newsapi.WsjFragment;

import java.util.ArrayList;
import java.util.List;

public class NytTabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public NytTabAdapter(FragmentManager fm) {
        super(fm);
        provideTabbedFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void provideTabbedFragments() {
        fragments.clear();
        addFragment(NytNewsFragment.Companion.newInstance("home"), "Top Stories");
        addFragment(NytNewsFragment.Companion.newInstance("opinion"), "Opinion");
        addFragment(NytNewsFragment.Companion.newInstance("business"), "Business");
        addFragment(NytNewsFragment.Companion.newInstance("national"), "National");
        addFragment(NytNewsFragment.Companion.newInstance("world"), "World");
        addFragment(NytNewsFragment.Companion.newInstance("politics"), "N.Y.");
        addFragment(NytNewsFragment.Companion.newInstance("nyregion"), "U.S.");
        addFragment(NytNewsFragment.Companion.newInstance("sundayreview"), "Sunday Review");
        addFragment(NytNewsFragment.Companion.newInstance("sports"), "Sports");
        addFragment(NytNewsFragment.Companion.newInstance("movies"), "Movies");
        addFragment(NytNewsFragment.Companion.newInstance("arts"), "Arts");
        addFragment(NytNewsFragment.Companion.newInstance("travel"), "Travel");
        addFragment(NytNewsFragment.Companion.newInstance("health"), "Heath");
        addFragment(NytNewsFragment.Companion.newInstance("magazine"), "Magazine");
        addFragment(NytNewsFragment.Companion.newInstance("technology"), "Technology");
        addFragment(NytNewsFragment.Companion.newInstance("realestate"), "Real Estate");
        notifyDataSetChanged();
    }

    private void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }
}