package com.wzz.rxjava_okhttp_retrofit.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 简单使用Rxjava：
 * 1、Observable.just("haha", "ddd");
 *    Observable.create()
 *
 * 2、 observable.subscribe( observer );
 */
public class RxjavaSimpleActivity extends AppCompatActivity {

    private String TAG = "wzz------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  observable.subscribe( observer );
        Observable<String> observable = getObservable();
        Observer observer = getObserver();

        // 1、通过subscribe注册Observer
        observable.subscribe( observer );

        // 2、通过Consumer
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "onNext: " + s );
            }
        });
    }

    private Observer getObserver(){

        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
    /**
     * 创建Observable
     * @return
     */
    private Observable<String> getObservable() {

        // 发送多个事件
//         return Observable.just("haha", "ddd");
//         return  Observable.fromArray("haha" , "xixixi" , "lalala") ;

         return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            /** 此方法是默认为主线程  */
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("哈哈哈");
                emitter.onNext("嘻嘻嘻");
            }

        });

    }
}
