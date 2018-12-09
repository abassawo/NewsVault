package com.abasscodes.newsy.data.rest;

import com.abasscodes.newsy.models.NytResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NytApi {
    @GET("/svc/topstories/v2/{section}.json")
    Single<NytResponse> getNytFeed(@Path("section") String section);
}
