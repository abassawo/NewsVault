package com.abasscodes.newsy.data.rest;

import com.abasscodes.newsy.models.NewsApiResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("/v2/top-headlines/")
    Single<NewsApiResponse> getTopHeadlines(@Query("sources") String query);

    @GET("/v2/everything/")
    Single<NewsApiResponse> getAllWsjFeed(@Query("sources") String query);

//    @GET("/everything?sources=the-wall-street-journal")
//    Call<NewsApiResponse> getAllWsjFeed();
}
