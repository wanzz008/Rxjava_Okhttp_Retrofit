package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.test;

import android.app.AlertDialog;
import android.content.Context;

import java.lang.ref.WeakReference;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer的实现类
 * 在使用的时候，只需要重写一个onSuccess(T)方法即可，方便了很多
 *
 * 使用:  subscribe(new HttpObserver)
 *
 * @param <T>
 */
public abstract class HttpObserver<T>  implements Observer<T> , onRequestListener<T>{
    @Override
    public void onSubscribe(Disposable d) {
        showProgressDialog();
    }


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }


    @Override
    public void onError(Throwable e) {
        /*需要緩存并且本地有缓存才返回*/
        errorDo(e);
        dismissProgressDialog();
    }

    // ------------------------------------------
    //    弱引用防止内存泄露
    private WeakReference<Context> mActivity;
    //    加载框可自己定义
    private AlertDialog mDialog = null;

    public HttpObserver(Context mActivity) {
        this.mActivity = new WeakReference<>( mActivity );
        initProgressDialog();
    }


    @Override
    public void onHttpError(Throwable error) {
        //这里可以做错误处理，也可以在调用的时候重写error方法，对错误信息进行处理
//        ApiError error1 = FactoryException.analysisExcetpion(error);
//        ToastUtil.showToastLong(mActivity.get(),error.getDisplayMessage());
    }

    /**
     * 错误统一处理
     *
     * @param e
     */
    private void errorDo(Throwable e) {

        onHttpError( e );

//        Context context = mActivity.get();
        //if (context == null) return;
//        if (e instanceof ApiError) {
//            onError((ApiError) e);
//        } else if (e instanceof HttpTimeException) {
//            HttpTimeException exception=(HttpTimeException)e;
//            onError(new ApiError(exception, CodeException.RUNTIME_ERROR,exception.getMessage()));
//        } else {
//            onError(new ApiError(e, CodeException.UNKNOWN_ERROR,e.getMessage()));
//        }
        /*可以在这里统一处理错误处理-可自由扩展*/

    }




    private void initProgressDialog() {
        Context context = mActivity.get();
        if (mDialog == null && context != null) {

            mDialog = new SpotsDialog.Builder()
                    .setContext(context)
                    .build();
        }
    }



    private void showProgressDialog() {
        Context context = mActivity.get();
        if (mDialog == null || context == null) return;
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
