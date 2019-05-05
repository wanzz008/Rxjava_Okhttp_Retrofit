package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.test;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *         作者：HUandroid
 *         来源：CSDN
 *         原文：https://blog.csdn.net/HUandroid/article/details/79883895
 *         版权声明：本文为博主原创文章，转载请附上博文链接！
 */

class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        /** 1、第1种方式 */
//        Request request1 = chain.request()
//                .newBuilder()
//                .addHeader("userDeviceID", MyApplication.DEVICETOKEN )
//                .header("LoginUser-Agent", "android-27")
//                .build();
//        return chain.proceed(request1);

        /** 2、第2种方式 */
        Request request = chain.request();
        HttpUrl httpUrl = request.url()
                .newBuilder()
                // add common parameter
                .addQueryParameter("token", "123")
                .addQueryParameter("username", "tt")
                .build();

        Request build = request.newBuilder()
                // add common header
                .addHeader("contentType", "text/json")
                .header("LoginUser-Agent", "android-27")
                .url(httpUrl)
                .build();
        Response response = chain.proceed(build);
        return response;
    }
}
