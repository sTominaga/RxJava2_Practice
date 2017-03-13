package com.example.ptn0411.rxjava2practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ptn0411 on 2017/03/07.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = ApiClient.create();

        Single.create((SingleOnSubscribe<String>) emitter -> {
            try {
                emitter.onSuccess("sTominaga");
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
        .subscribeOn(Schedulers.trampoline())
        .observeOn(Schedulers.trampoline())
        .subscribe(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String value) {
                GitHubService service = retrofit.create(GitHubService.class);
                Call<User> call = service.getUser(value);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            Log.d("MainActivity",user.getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("MainActivity", "onFailure");
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MainActivity", "onError");
            }
        });
    }
}
