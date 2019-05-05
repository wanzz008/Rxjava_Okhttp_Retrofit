package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.test;

public interface onRequestListener<T> {
    void onSuccess(T t);

    void onHttpError(Throwable error);

}
