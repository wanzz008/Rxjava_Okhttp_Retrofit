package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.R;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginParam;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginResult;
import com.wzz.rxjava_okhttp_retrofit.bean.User;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
public class Retrofit_Rxjava_Activity extends AppCompatActivity {

    private Api api;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rxjava);

        mTextView = findViewById(R.id.text) ;

        String url = "https://www.apiopen.top/" ;
//                "login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create()) // Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 结合使用Rxjava和Retrofit
                .build();

        api = retrofit.create(Api.class);

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

    /**
     * 结合使用Retrofit和Rxjava
     * @param view
     */
    public void click_two1(View view) {

        api.loginWithRx( getLoginParam() )
                .flatMap(new Function<LoginResult, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(LoginResult loginResult) throws Exception {
                        return api.getUserWithRx( loginResult.id );
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {

                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {

                    }
                });
    }
}
