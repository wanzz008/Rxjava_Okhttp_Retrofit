package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.download;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 用于只暴露success和error
 * Created by zhongjh on 2018/5/18.
 * @param <T>
 */
public abstract class BaseDownloadObserver<T> implements Observer<T> {
    @Override
    public abstract void onSubscribe(Disposable d);

    @Override
    public void onNext(T t) {
        onDownloadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownloadError(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onDownloadSuccess(T t);

    protected abstract void onDownloadError(Throwable e);
}
