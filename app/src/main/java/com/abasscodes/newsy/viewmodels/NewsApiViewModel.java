package com.abasscodes.newsy.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.abasscodes.newsy.data.rest.NewsApi;
import com.abasscodes.newsy.models.NewsApiResponse;
import com.abasscodes.newsy.data.rest.RetrofitClient;
import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiSource;
import com.abasscodes.newsy.utils.rx.RxUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsApiViewModel extends ViewModel {

    private static final String TAG = NytArticlesViewModel.class.getSimpleName();
    private MutableLiveData<Map<String, List<NewsApiResponse.Article>>> articles = new MutableLiveData<>();
    private MutableLiveData<List<NewsApiResponse.Article>>allArticles = new MutableLiveData<>();
    private Map<String, List<NewsApiResponse.Article>> map = new HashMap<>();
    private NewsApi api = RetrofitClient.getWsjApi();

    public MutableLiveData<Map<String, List<NewsApiResponse.Article>>> getTopArticles(final String source) {
        api.getTopHeadlines(source).compose(RxUtil.subscribeOnIoObserveOnUi())
                .subscribe(response -> {
                    map.put(source, response.getArticles());
                    articles.postValue(map);
                }, throwable -> Log.d(TAG, throwable.getMessage()));
        return articles;

    }

    public MutableLiveData<List<NewsApiResponse.Article>> getAllArticles(String source) {
        api.getAllWsjFeed(source).compose(RxUtil.subscribeOnIoObserveOnUi()).subscribe(response -> {
            if (response != null) {
                    //for(NewsApiResponse.Article article : response.getArticles()) {
                        //if(article.getUrl().contains("arabic.cnn.com")) {
                        //    response.getArticles().remove(article);
                        //}
                    //}
                this.allArticles.postValue(response.getArticles());
            }
        }, throwable -> Log.d(TAG, "Network error " + throwable.getMessage()));
        return allArticles;
    }
}
