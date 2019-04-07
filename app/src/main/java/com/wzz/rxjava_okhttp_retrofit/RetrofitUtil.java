package com.wzz.rxjava_okhttp_retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private Api mTestService;
    private RetrofitUtil(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTestService = mRetrofit.create(Api.class);
    }
    public static RetrofitUtil getInstance(){
        if (sInstance == null){
            synchronized(RetrofitUtil.class){
                if (sInstance == null){
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }
    public Api getTestService(){
        return mTestService;
    }
}