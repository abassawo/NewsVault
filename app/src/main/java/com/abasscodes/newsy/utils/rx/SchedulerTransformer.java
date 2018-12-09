package com.abasscodes.newsy.utils.rx;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;

public class SchedulerTransformer<R> implements CombinedTransformer<R> {
    @NonNull
    private final Scheduler ioScheduler;
    @NonNull
    private final Scheduler uiScheduler;

    public SchedulerTransformer(@NonNull Scheduler ioScheduler, @NonNull Scheduler uiScheduler) {
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream.subscribeOn(ioScheduler).observeOn(uiScheduler);
    }

    @Override
    public Publisher<R> apply(Flowable<R> upstream) {
        return upstream.subscribeOn(ioScheduler).observeOn(uiScheduler);
    }

    @Override
    public MaybeSource<R> apply(Maybe<R> upstream) {
        return upstream.subscribeOn(ioScheduler).observeOn(uiScheduler);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return upstream.subscribeOn(ioScheduler).observeOn(uiScheduler);
    }

    @Override
    public SingleSource<R> apply(Single<R> upstream) {
        return upstream.subscribeOn(ioScheduler).observeOn(uiScheduler);
    }
}
