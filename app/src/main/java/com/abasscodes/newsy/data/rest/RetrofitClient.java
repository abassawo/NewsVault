package com.abasscodes.newsy.data.rest;

import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.abasscodes.newsy.BuildConfig;
import com.abasscodes.newsy.NewsyApplication;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("SameParameterValue")
public final class RetrofitClient {

    // TODO: change this to a real endpoint
    private static final String NYT_URL = "https://api.nytimes.com/";
    private static final String WSJ_URL = "https://newsapi.org/";
    private static final String NYT_API_KEY = "a93de1bfecf442a686267fb02149b275";
    private static final String WSJ_API_KEY = "21d3078465f64ef8b53752a32a4110ee";
    private static final int CONNECTION_TIMEOUT = 30;
    private static final String TAG = RetrofitClient.class.getSimpleName();

    private static NytApi nytInstance;
    private static NewsApi wsjInstance;

    public synchronized static NytApi getNytApi() {
        if (nytInstance == null) {
            nytInstance = createNytApi();
        }
        return nytInstance;
    }

    public synchronized static NewsApi getWsjApi() {
        if (wsjInstance == null) {
            wsjInstance = createWsjApi();
        }
        return wsjInstance;
    }

    @VisibleForTesting
    static NytApi getTestApi(String baseUrl) {
        return createNytApi();
    }

    private RetrofitClient() {
    }

    private static NewsApi createWsjApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(NewsyApplication.getCache());
        if (BuildConfig.LOG_CONSOLE) {
            builder.addInterceptor(new HttpLoggingInterceptor(message -> Log.v(TAG, message)));
        }
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", WSJ_API_KEY)
                    .build();

            Log.d(TAG, url.toString());
            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        OkHttpClient httpClient = builder
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(WSJ_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsApi.class);
    }


    private static NytApi createNytApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.LOG_CONSOLE) {
            builder.addInterceptor(new HttpLoggingInterceptor(message -> Log.d(TAG, message)).setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api-key", NYT_API_KEY)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        OkHttpClient httpClient = builder
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(NYT_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NytApi.class);
    }
}
