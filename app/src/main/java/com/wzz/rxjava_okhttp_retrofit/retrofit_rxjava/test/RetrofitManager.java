package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.test;


import android.os.Environment;

import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginResult;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    //OkHttp-- 30秒内直接读缓存
    private static final long HAVA_NET_MAX = 30; //30秒  有网超时时间
    //OkHttp--设置链接与写入超时时间
    private static final int CONNECT_TIMEOUT = 10;

    //定义同步的OkHttpClient
    private static volatile OkHttpClient sOkHttpClient = null;
    //定义RetrofitManager
    private static RetrofitManager mInstance = null;
    //定义
    private final Api mService;

    // 配置OkHttpClient
    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    //创建OkHttpClient并配置
                    sOkHttpClient = new OkHttpClient.Builder()
                            .cache( initOkHttpCache() ) //配置缓存
//                            .addNetworkInterceptor(new NetCacheInterceptor(App.getContext())) //添加网络拦截器
                            .retryOnConnectionFailure(true)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(HAVA_NET_MAX, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    //配置okhttp缓存
    private Cache initOkHttpCache() {
        int cacheSize = 1024 * 1024 * 30; //缓存大小30Mb
        //储存目录 指定缓存路径
        File directoryFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/okHttpCache");
        return new Cache(directoryFile, cacheSize);
    }

    //创建及配置Retrofit
    private RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .client(getOkHttpClient()) //设置自定义配置的OkHttpClient
                .addConverterFactory(GsonConverterFactory.create()) //添加json解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加RxJava2的适配
                .build();
        mService = retrofit.create(Api.class);
    }

    //获取retrofit
    public static RetrofitManager getRetrofit() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 1.登录
     */
    public Observable<LoginResult> login(String userName, String password) {
        return mService.loginKgRx(userName, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }


 }
