package com.abasscodes.newsy.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.abasscodes.newsy.screens.mainscreen.dashboard.DashboardCardViewFactory;
import com.abasscodes.newsy.screens.mainscreen.dashboard.DashboardView;
import java.util.List;

public class DashboardCardViewModel extends ViewModel {

  private MutableLiveData<List<DashboardView>> dashboardViews = new MutableLiveData<>();

  public MutableLiveData<List<DashboardView>> getDashboardViews(Context context) {
    dashboardViews.postValue(DashboardCardViewFactory.getPermittedCards(context));
    return dashboardViews;
  }
}