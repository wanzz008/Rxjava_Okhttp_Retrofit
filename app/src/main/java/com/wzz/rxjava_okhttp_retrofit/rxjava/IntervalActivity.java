package com.wzz.rxjava_okhttp_retrofit.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wzz.rxjava_okhttp_retrofit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 验证码的案例：
 * 操作符：
 * Interval:
 * 创建一个按固定时间间隔发射整数序列的Observable
 */
public class IntervalActivity extends AppCompatActivity {

    private Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);

        mBt = findViewById(R.id.bt) ;

    }

    /**
     *
     * 发送验证码倒计时的案例：
     *
     *  interval: 创建一个按固定时间间隔发射整数序列的Observable
     *      它接受一个表示时间间隔的参数和一个表示时间单位的参数。
     *
     *  take:
     *      使用 Take 操作符让你可以修改Observable的行为，只返回前面的N项数据，然后发射完成通
     *  知，忽略剩余的数据。
     *
     *  doOnSubscribe：
     *      注册一个动作，在观察者订阅时使用。
     */
    int count = 10 ;
    public void send(View view) {
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(count + 1 )  // 只发送前count+1的数据
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return  count - aLong ;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // 必须为主线程
                // 注册一个动作，在观察者订阅时使用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mBt.setEnabled( false ); // 在发送验证码后10s钟内不许点击按钮
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d("wzz-----", "onNext..... " + aLong);
                        mBt.setText( "还剩" + aLong + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("wzz-----", "onComplete..... ");
                        mBt.setEnabled( true );
                        mBt.setText("重新发送验证码");
                    }
                });
    }


    public void test(){

        Observable.just("1","2","3","4","5","6")
                .take( 4 ); // 只发送前4个数据

    }
}
