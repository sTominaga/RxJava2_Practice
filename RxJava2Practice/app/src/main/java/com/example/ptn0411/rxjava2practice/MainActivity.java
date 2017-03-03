package com.example.ptn0411.rxjava2practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReactiveStreams();
            }
        });
    }

    private void startReactiveStreams() {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                String[] datas = {"Hello, World!", "こんにちは、世界!"};

                for (String data : datas) {
                    //購読解除されている場合は処理をやめる
                    if (e.isCancelled()) {
                        return;
                    }
                    //データを通知する
                    e.onNext(data);
                }

                //完了したことを通知する
                e.onComplete();
            }
            //超過したデータはバッファする
        }, BackpressureStrategy.BUFFER);

        flowable
                //Subscriberの処理を別スレッドで行うようにする
                .observeOn(Schedulers.computation())
                //購読する
                .subscribe(new Subscriber<String>() {

                    //データ数のリクエスト、購読の解除を行うオブジェクト
                    private Subscription subscription;

                    //購読が開始された時の処理
                    @Override
                    public void onSubscribe(Subscription s) {
                        //SubscriptionをSubscriber内で保持する
                        subscription = s;

                        //受け取るデータ数をリクエスト
                        subscription.request(1L);
                    }

                    //データを受け取った時の処理
                    @Override
                    public void onNext(String s) {
                        //実行しているスレッド名を取得する
                        String threadName = Thread.currentThread().getName();

                        //⬆︎で取得したデータをLogで出力する
                        Log.d("MainActivity", threadName+":"+s);

                        //次に受け取るデータ数をリクエストする
                        subscription.request(1L);
                    }

                    //エラーを通知された時の処理
                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    //完了を通知された時の処理
                    @Override
                    public void onComplete() {
                        //実行しているスレッド名の取得
                        String threadName = Thread.currentThread().getName();
                        Log.d("MainActivity", threadName+":完了しました");
                    }
                });

        //しばらくまつ
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
