package com.example.ptn0411.rxjava2practice.model.api;

import com.example.ptn0411.rxjava2practice.model.api.entity.RepositoryEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ptn0411 on 2017/03/08.
 */

public interface GithubServiceApi {
    /*
     *自分のリポジトリ一覧を取得
     */
    @GET("users/{user}/repos")
    Observable<RepositoryEntity> callRepoFromGithub(@Path("user") String user);
}
