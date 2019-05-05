package com.wzz.rxjava_okhttp_retrofit.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.R;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginParam;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginResult;
import com.wzz.rxjava_okhttp_retrofit.bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 操作符：
 * Map
 * FlatMap
 * Rxjava和Retrofit结合使用
 */
public class MapActivity extends AppCompatActivity {

    private Api api;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mTextView = findViewById(R.id.text) ;

        String url = "https://www.apiopen.top/" ;
//                "login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 结合使用Rxjava和Retrofit
                .build();

        api = retrofit.create(Api.class);

    }

    /**
     * 把list中的元素，一个个发送出去
     */
    public void fromArray(){

        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");

        Observable.just(list)
                .map(new Function<List<String>, String[]>() {
                    @Override
                    public String[] apply(List<String> strings) throws Exception {
                        String[] aa = new String[strings.size()]; //list转为数组
                        return strings.toArray( aa );
                    }
                })
                .flatMap(new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String[] strings) throws Exception {
                        return Observable.fromArray(strings);
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("wzz------", "doOnNext: " + s );
                        Thread.sleep(3000); // 操作
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("wzz------", "accept: " + s );
                    }
                });

    }

    /**
     * Map :
     * 操作符对原始Observable发射的每一项数据应用一个你选择的函数，然后返回一个发射这些结果的Observable。
     * @param view
     */
    public void click_map(View view) {
        // Map
        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + " hello";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("wzz-------", "accept: " + s);
                    }
            });
    }

    /**
     * FlatMap :
     * 将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据合并
     * 后放进一个单独的Observable
     * @param view
     */
    public void click_FlatMap(View view) {

        Observable.just(getLoginParam())
                .flatMap(new Function<LoginParam, ObservableSource<LoginResult>>() {
                    @Override
                    public ObservableSource<LoginResult> apply(LoginParam param) throws Exception {
                        /** 把登陆的结果 通过Observable再返回 */
                        LoginResult loginResult = api.login(param).execute().body();
                        return Observable.just( loginResult );
                    }
                })
                .flatMap(new Function<LoginResult, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(LoginResult loginResult) throws Exception {
                        User user = api.getUserInfoWithPath(loginResult.id).execute().body();
                        return Observable.just( user );
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mTextView.setText( user.getMsg() );
                    }
                });
    }

    /**
     *
     * @return
     */
    private LoginParam getLoginParam(){
        return new LoginParam("1" , "2" ,"3");
    }

    /**
     * 结合使用Retrofit和Rxjava
     * @param view
     */
    public void click_two(View view) {

        Observable.just(getLoginParam())
                .flatMap(new Function<LoginParam, ObservableSource<LoginResult>>() {
                    @Override
                    public ObservableSource<LoginResult> apply(LoginParam param) throws Exception {
                        /** 把登陆的结果 通过Observable再返回 */
                        return api.loginWithRx( param );
                    }
                })
                .flatMap(new Function<LoginResult, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(LoginResult loginResult) throws Exception {
                        return api.getUserWithRx( loginResult.id );
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mTextView.setText( user.getMsg() );
                    }
                });
    }
}
