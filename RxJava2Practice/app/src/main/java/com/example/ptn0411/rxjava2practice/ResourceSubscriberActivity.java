package com.example.ptn0411.rxjava2practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/*----------------------------------------------------------------------
  ResourceSubscriber
  onSubscribeメソッドでLong.MAX_VALUEをリクエストすることを内部で行うクラス。
  このResourceSubscriberは抽象クラスであり、RxJava1.xのObserverと同様に
  以下のメソッド飲み実装すればよい（データ数のリクエストを忘れることを防げる）

  ・onNext(T data)
  ・onError(Throwable error)
  ・onComplete()

  また、ResourceSubscriberではonSubscribeメソッドの実装がfinalで定義されていて、
  受け取ったSubscriptionが隠蔽されている。
  そのため、当クラスを使う場合は直接Subscriptionにアクセスすることはできない
  代わりに(?)、onSubscribeメソッドないから呼ばれている
  ResourceSubscriberのonStartメソッドをオーバーライドすることで
  初期のリクエストを行うことが出来る。

  さらに、Disposableを実装しているため、購読を解除するdisposeメソッドも実装している
  内部でSubscriptionのcancelメソッドを呼んでいるため、Subscriptionに直接アクセスできなくても
  外部からdisposeメソッド経由で購読の解除を行うことが出来る。

 ----------------------------------------------------------------------*/

public class ResourceSubscriberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startReactiveStreams();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startReactiveStreams() throws InterruptedException {
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
        /*---------------------------------------------
          Flawable.create()の第二引数
          BackpressureStrategy.BUFFER
           - 生成されたデータを通知するまで全て貯めておく
          BackpressureStrategy.DROP
           - 通知待ちとなったデータを破棄する
          BackpressureStrategy.LATEST
           - 全てではなく、最新のデータのみ保持する
         ---------------------------------------------*/
            //超過したデータはバッファする
        }, BackpressureStrategy.BUFFER);

        flowable
                //Subscriberの処理を別スレッドで行うようにする
                .observeOn(Schedulers.computation())
                //購読する
                .subscribe(new ResourceSubscriber<String>() {

                    /* 購読の開始時間 */
                    private long startTime;

                    //購読が開始された際の処理
                    @Override
                    protected void onStart() {
                        //購読の開始時間を取得
                        startTime = System.currentTimeMillis();
                        //データ数のリクエスト
                        request(Long.MAX_VALUE);
                    }

                    //データを受け取った際の処理
                    @Override
                    public void onNext(String s) {
                        Log.d("ResourceSubscriber", "onNext called");
                        //購読開始から500ミリ秒を過ぎた場合は購読を解除する
                        if ((System.currentTimeMillis() - startTime) > 500L) {
                            dispose(); //購読を解除する
                            Log.d("ResourceSubscriber", "購読解除しました");
                            return;
                        }

                        try {
                            Thread.sleep(1500L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        String threadName = Thread.currentThread().getName();
                        Log.d("ResourceSubscriber", threadName+":完了しました:"+ s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
