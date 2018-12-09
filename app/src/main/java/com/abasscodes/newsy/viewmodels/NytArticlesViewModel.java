package com.abasscodes.newsy.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abasscodes.newsy.data.repository.NytRepository;
import com.abasscodes.newsy.models.NytResponse;
import com.abasscodes.newsy.screens.newssources.nytimes.NytNewsFragment;

import java.util.List;

public class NytArticlesViewModel extends AndroidViewModel {

    private MutableLiveData<List<NytResponse.Result>> results = new MutableLiveData<>();
    private NytRepository repository;

    public NytArticlesViewModel(@NonNull Application application) {
        super(application);
        repository = new NytRepository(application);
    }

    public void getResults(String section) {
        repository.getResults(section, this);
    }

    public void setResults(List<NytResponse.Result> results) {
        this.results.postValue(results);

    }

    public MutableLiveData<List<NytResponse.Result>> getResults() {
        return results;
    }
}