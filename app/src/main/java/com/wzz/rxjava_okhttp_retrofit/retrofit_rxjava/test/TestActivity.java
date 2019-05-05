package com.wzz.rxjava_okhttp_retrofit.retrofit_rxjava.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.R;
import com.wzz.rxjava_okhttp_retrofit.RetrofitUtil;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginResult;

import io.reactivex.disposables.Disposable;


/**
 * Created by _wzz on 2016/5/11.
 */
public class TestActivity extends AppCompatActivity {


	Disposable mDisposable ;
	public Api api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main );

		api = RetrofitUtil.getInstance().getApi();

		api.loginKgRx("wc", "123456").subscribe(new HttpObserver<LoginResult>(this) {


            @Override
            public void onSuccess(LoginResult loginResult) {

            }

        });


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("wzz----", "onDestroy.... " );
		if ( mDisposable != null ){
			mDisposable.dispose();
		}

	}

}
