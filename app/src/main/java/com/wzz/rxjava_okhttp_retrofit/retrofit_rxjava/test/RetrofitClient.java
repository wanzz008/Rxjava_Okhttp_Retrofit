package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.test;


import com.wzz.rxjava_okhttp_retrofit.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  来源：CSDN
 *     原文：https://blog.csdn.net/HUandroid/article/details/79883895
 *     版权声明：本文为博主原创文章，转载请附上博文链接！
 *
 * 添加OkHttp参数
 *  * 你们所知道的，retrofit2内部网络请求使用的就是okhttp，光是用上面的retrofit2往往是不够全面的，还需要添加okhttp参数：
 *  *
 *  * okhttp支持网络请求的信息打印，这样更方便查看网络请求信息。在项目中build.gradle文件中引入logging-interceptor：
 *  *  compile 'com.squareup.okhttp3:logging-interceptor:3.4.1'
 *  *
 */

public class RetrofitClient {
    private volatile static RetrofitClient sInstance;
    private Retrofit mRetrofit;
    private Api mApi;

    /**
     *
     * addInterceptor   设置拦截器
     * cookieJar    设置cook管理类
     * readTimeout   设置读取超时时间
     * writeTimeout  设置写的超时时间
     * connectTimeout  设置链接超时时间
     * retryOnConnectionFailure 设置是否重试链接
     */

    private RetrofitClient(){

        // okhttp支持网络请求的信息打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MyInterceptor()) // 自定义的拦截器
                .addInterceptor( loggingInterceptor )
//            .cookieJar(new CookiesManager()) // cookieJar 是cook持久化管理，用于免登陆使用
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client( okHttpClient )
                .baseUrl("http://manage.lkyj.com.cn/WebServices/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mApi = mRetrofit.create(Api.class);

    }
    public static RetrofitClient getInstance(){
        if (sInstance == null){
            synchronized(RetrofitClient.class){
                if (sInstance == null){
                    sInstance = new RetrofitClient();
                }
            }
        }
        return sInstance;
    }
    public Api getApi(){
        return mApi;
    }
}