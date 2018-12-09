package com.abasscodes.newsy.base;
import android.support.annotation.NonNull;
//import com.abasscodes.newsy.models.NewsyRepository;
import com.abasscodes.newsy.settings.UserSettings;

import io.reactivex.Scheduler;


public class PresenterConfiguration {
    @NonNull
    private final Scheduler ioScheduler;
    @NonNull
    private final Scheduler uiScheduler;
    @NonNull
    private final Object repository;
    @NonNull
    private final UserSettings userSettings;

    public PresenterConfiguration(@NonNull Scheduler ioScheduler,
                                  @NonNull Scheduler uiScheduler,
                                  @NonNull Object repository,
                                  @NonNull UserSettings settings) {
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.repository = repository;
        this.userSettings = settings;
    }

//    @NonNull
//    public GettyImageRepository getRepository() {
//        return repository;
//    }
//
//    @NonNull
//    public Scheduler getIoScheduler() {
//        return ioScheduler;
//    }
//
//    @NonNull
//    public Scheduler getUiScheduler() {
//        return uiScheduler;
//    }
//
//    @NonNull
//    public GettyImageRepository getService() {
//        return repository;
//    }

    public UserSettings getUserSettings() {
        return userSettings;
    }
}
