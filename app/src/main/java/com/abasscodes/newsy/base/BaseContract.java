package com.abasscodes.newsy.base;

public interface BaseContract {

    interface View {
        void showError();
    }

    interface Presenter<T extends View> {

    }
}
