package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.download;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 用于线程传递ui的
 * Created by zhongjh on 2018/5/18.
 */
class DownloadHandler {

    // handle ,处理ui方面的onProgress
    private Handler mHandler;

    private static final int WHAT_UPDATE = 0x01; // 更新进度
    private static final String PROGRESS = "progress";

    private DownloadListener mDownloadListener;

    /**
     * 初始化handler
     */
    void initHandler(final DownloadListener downloadListener) {
        if (mHandler != null) {
            return;
        }
        mDownloadListener = downloadListener;
        // 同步锁此类
        synchronized (DownloadHandler.class) {
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg == null) {
                            return;
                        }
                        switch (msg.what) {
                            case WHAT_UPDATE:
                                Bundle updateData = msg.getData();
                                if (updateData == null) {
                                    return;
                                }
                                int progress = updateData.getInt(PROGRESS);
                                mDownloadListener.onProgress(progress);
                                break;
                            default:
                                break;
                        }
                    }
                };
            }
        }
    }

    /**
     * 传递进度给ui
     * @param progress 进度，100为满
     */
    void onProgress(int progress) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mDownloadListener.onProgress(progress);
            return;
        }
        Message message = mHandler.obtainMessage();
        message.what = WHAT_UPDATE;
        Bundle data = new Bundle();
        data.putInt(PROGRESS,  progress);
        message.setData(data);
        mHandler.sendMessage(message);
    }

}
