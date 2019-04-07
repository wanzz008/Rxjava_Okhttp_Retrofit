package com.wzz.rxjava_okhttp_retrofit;

import com.wzz.rxjava_okhttp_retrofit.bean.LoginParam;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginResult;
import com.wzz.rxjava_okhttp_retrofit.bean.User;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Ivan on 2016/11/14.
 */

public interface Api {

    // 目标URL:
    //     login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456

    /**
     * @Query的方式
     */
    @GET("login")
    Call<User> getUserInfoWithQuery(@Query("key") String key , @Query("phone") String phone , @Query("passwd") String passwd );

    /**
     * @QueryMap的方式
     */
    @GET("login")
    Call<User> getUserInfoWithMap(@QueryMap Map<String,String> params );

    /**
     * @Path的方式
     */
    @GET("login/user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);

    /**
     * @Field的方式
     */
    @FormUrlEncoded
    @POST("login")
    Call<User> getUserInfoWithField(@Field("key") String key ,@Field("phone") String phone ,@Field("passwd") String passwd  );


    // -----------------------------------------------------------

    @POST("login")
    Call<User> getUserInfoWithBody(@Body LoginParam param);


    // -----------------------------------------------------

    @GET("login")
    Call<LoginResult> login(@Body LoginParam param);


    @GET("login")
    Observable<LoginResult> loginWithRx(@Body LoginParam param);

    @GET("login/user/{id}")
    Observable<User> getUserWithRx(@Path("id") int user_id);

}
