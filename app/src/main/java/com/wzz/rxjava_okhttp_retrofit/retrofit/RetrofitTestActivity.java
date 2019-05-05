package com.wzz.rxjava_okhttp_retrofit.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.R;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginParam;
import com.wzz.rxjava_okhttp_retrofit.bean.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitTestActivity extends AppCompatActivity {

    public Api api ;
    TextView textView;
    private String TAG = "wzz--------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_test);
        textView = findViewById(R.id.text);

        String url = "https://www.apiopen.top/" ;
//      "login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

    }

    public void click(View view) {

        // URL: https://www.apiopen.top/login?key=00d91e8&phone=13594347817&passwd=123456
        /** 用@Query的方式做Get请求 */
//        requestWithQuery();
//        /** 用@QueryMap的方式做Get请求 */
//        requestWithMap();

//        requestWithBody();
        requestWithField();

    }

    /**
     *  用@Query的方式做Get请求
     */
    private void requestWithQuery() {

        Call<User> call = api.getUserInfoWithQuery("00d91e8e0cca2b76f515926a36db68f5","13594347817","123456");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d(TAG, "onResponse1: " + user);
                textView.append( user.getMsg() );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    /**
     * 用@QueryMap的方式做Get请求
     */
    private void requestWithMap() {

        Map<String ,String> map = new HashMap<>();
        map.put("key", "1111");
        map.put("phone", "1111");
        map.put("passwd", "1111");
        Call<User> call = api.getUserInfoWithMap(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d(TAG, "onResponse2: " + user);
                textView.append( user.getMsg() );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    /**
     * 用@Body的方式做Get请求
     */
    private void requestWithBody() {

        LoginParam loginParam = new LoginParam();
        loginParam.key= "1";
        loginParam.phone= "1";
        loginParam.passwd= "1";


        Call<User> call = api.getUserInfoWithBody(loginParam);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d(TAG, "onResponse2: " + user);
                textView.append( user.getMsg() );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    /**
     * 用@Body的方式做Get请求
     */
    private void requestWithField() {

        Call<User> call = api.getUserInfoWithField("1" , "2" ,"3");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d(TAG, "onResponse2: " + user);
                textView.append( user.getMsg() );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
