package com.abasscodes.newsy.base;

import android.support.annotation.NonNull;


public abstract class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter<T> {

    protected T view;

    private boolean isViewBound = false;

    public BasePresenter(@NonNull T view) {
        this.view = view;
    }

}
