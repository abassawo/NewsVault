package com.abasscodes.newsy.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.abasscodes.newsy.NewsyApplication;

public abstract class BaseFragment extends Fragment implements BaseContract.View {

  @Override
  public final View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
    return inflater.inflate(getLayoutResourceId(), container, false);
  }

  @Override
  @CallSuper
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  protected abstract int getLayoutResourceId();

  @Override
  public void showError() {
    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG)
      .show();
  }

  protected final NewsyApplication getNewsyApplication() {
    return (NewsyApplication) getActivity().getApplication();
  }
}
