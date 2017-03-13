package com.example.ptn0411.rxjava2practice.model.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ptn0411 on 2017/03/13.
 */

public class ApiClient {

    public static Retrofit create() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("Accept", "application/json");

            request = requestBuilder.build();
            Response response = chain.proceed(request);

            return response;
        });

        HttpLoggingInterceptor apiLog = new HttpLoggingInterceptor();
        apiLog.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClientBuilder.addInterceptor(apiLog);
        OkHttpClient client = httpClientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

}
