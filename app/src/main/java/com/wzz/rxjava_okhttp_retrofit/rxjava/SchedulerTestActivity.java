package com.wzz.rxjava_okhttp_retrofit.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.R;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginParam;
import com.wzz.rxjava_okhttp_retrofit.bean.User;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SchedulerTestActivity extends AppCompatActivity {


    public Api api ;
    TextView textView;
    private String TAG = "wzz--------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_test);
        textView = findViewById(R.id.text);

        String url = "https://www.apiopen.top/" ;
//                "login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);


    }

    /**
     * 请求网络时的线程切换
     * @param view
     */
    public void click(View view) {

        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            // 此方法是默认为主线程
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {

                Call<User> call = api.getUserInfoWithBody(new LoginParam("00d91e8e0cca2b76f515926a36db68f5", "13594347817", "123456"));
                Response<User> response = call.execute();
                User user = response.body();
                Log.d(TAG, "onResponse1: " + user);
                emitter.onNext( user );

                // 网络请求
            }
        }).subscribeOn(Schedulers.io())  /** 让请求在子线程 */
                .observeOn(AndroidSchedulers.mainThread()) /** 让处理在主线程 */
                .subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {

                Log.d(TAG, "onResponse2: " + user);

                textView.append( user.toString() );
            }
        });

    }
}
