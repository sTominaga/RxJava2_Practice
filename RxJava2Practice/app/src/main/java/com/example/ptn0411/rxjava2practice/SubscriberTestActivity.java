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

/**
 * Created by ptn0411 on 2017/03/06.
 */

/*----------------------------------------------------------
  RxJava2.0 Subscriber
  通知されたデータを受け取り、そのデータを使って何らかの処理を
  行うインターフェース
  ・subscriberは受け取れるデータ数をリクエストすること
  　- データ数のリクエストをしないとFlowableはデータを通知することができない

  今回のサンプルでは、subscriberが1件のデータの処理を終えるまで、
  次のデータをsubscriberに通知しないようにFlowableの通知の制御
   (back pressure)を行っている
   この時の通知するデータ数のリクエストはsubsctiptionインターフェース経由で行う

   また、RxJava2.0から、subscribeメソッドでは戻り値を返さないように。
 ----------------------------------------------------------*/

public class SubscriberTestActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriber_test);

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
        }, BackpressureStrategy.BUFFER);

        flowable
            //Subscriberの処理を別スレッドで行うようにする
            .observeOn(Schedulers.computation())
            //購読する
            .subscribe(new Subscriber<String>() {

             /* データ数のリクエスト、購読の解除を行うオブジェクト */
                private Subscription subscription;

                /*----------------------------------------------
                 購読が開始された時の処理
                 RxJava1.xのObserbarと異なり、onSubscribeメソッドが追加
                 購読が開始されると最初に呼ばれる。
                 データ数のリクエスト及び、購読の解除を行う
                ----------------------------------------------*/
                @Override
                public void onSubscribe(Subscription s) {


                //SubscriptionをSubscriber内で保持する
                //onSubscriptionメソッドの引数(subscription)を
                //Subscriberのインスタンス変数に渡すことで、
                //onSubscribeが終了しても継続して使えるようにしている
                subscription = s;

                //受け取るデータ数をリクエスト
                //requestメソッドの呼び出しをonSubscribeメソッドの最後に行っている
                subscription.request(1L);
            }

                //データを受け取った時の処理
                @Override
                public void onNext(String s) {
                    //実行しているスレッド名を取得する
                    String threadName = Thread.currentThread().getName();

                    //取得したデータをLogで出力する
                    Log.d("Subscriber", threadName+":"+s);

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
                    Log.d("Subscriber", threadName+":完了しました");
                }
            });
    }
}
