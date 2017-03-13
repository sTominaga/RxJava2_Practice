package com.example.ptn0411.rxjava2practice.model.api;

import com.example.ptn0411.rxjava2practice.UserEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tominaga on 2017/03/11.
 */

public interface GitHubService {
    @GET("users/{username}")
    Call<UserEntity> getUser(@Path("username") String user);
}