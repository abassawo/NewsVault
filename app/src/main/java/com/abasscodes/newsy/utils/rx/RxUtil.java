package com.abasscodes.newsy.utils.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    private static final String TAG = RxUtil.class.getSimpleName();

    @NonNull
    public static Consumer<Throwable> logError() {
        return throwable -> Log.d(TAG, "Error occured with observable stream");
    }

    public static void unsubscribeDisposable(@Nullable Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static <R> SchedulerTransformer<R> subscribeOnIoObserveOnUi() {
        Scheduler ioScheduler = Schedulers.io();
        Scheduler uiScheduler = AndroidSchedulers.mainThread();
        return new SchedulerTransformer<>(ioScheduler, uiScheduler);
    }
}