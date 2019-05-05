package com.wzz.rxjava_okhttp_retrofit;

import com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.download.DownloadInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private Api mApi;

    // 超时15s
    private static final int DEFAULT_TIMEOUT = 15;


    /**
     * 带有下载监听的
     */
    private RetrofitUtil(){

        // okhttp支持网络请求的信息打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 下载文件的拦截器
        DownloadInterceptor mInterceptor = new DownloadInterceptor();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor) //f下载文件
                .addInterceptor( loggingInterceptor ) // log日志
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client( httpClient )
                .baseUrl("http://manage.lkyj.com.cn/WebServices/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mApi = mRetrofit.create(Api.class);

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
    public Api getApi(){
        return mApi;
    }
}