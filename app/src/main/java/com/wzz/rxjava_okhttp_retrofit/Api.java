package com.wzz.rxjava_okhttp_retrofit;

import com.wzz.rxjava_okhttp_retrofit.bean.LoginParam;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginResult;
import com.wzz.rxjava_okhttp_retrofit.bean.LoginUser;
import com.wzz.rxjava_okhttp_retrofit.bean.User;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Ivan on 2016/11/14.
 */

public interface Api {

//    Path是网址中的参数,例如:trades/{userId}
//    Query是问号后面的参数,例如:trades/{userId}?token={token}
//    QueryMap 相当于多个@Query
//    Field用于Post请求,提交单个数据,然后要加@FormUrlEncoded
//    Body相当于多个@Field,以对象的方式提交
//    @Streaming:用于下载大文件
//    @Header,@Headers、加请求头

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
     * @Path的方式  用于URL上占位符
     *
     *  形如?t=1&p=2&size=3的url链接不能用@PATH注解
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


    // ----------------------------------------Retrofit Rxjava-----------------------------------------------------------------


//    Path是网址中的参数,例如:trades/{userId}
//    Query是问号后面的参数,例如:trades/{userId}?token={token}
//    QueryMap 相当于多个@Query
//    Field用于Post请求,提交单个数据,然后要加@FormUrlEncoded
//    Body相当于多个@Field,以对象的方式提交
//    @Streaming:用于下载大文件
//    @Header,@Headers、加请求头


    String LOGIN  = "Option=Login&userName=%1$s&userPwd=%2$s";  // 老师登录，获取园所id和园所名称

    String getUser = "Option=GetAttendanceUser&Id=47&modifyTime=2010-09-17%2015:36:36";



    // 登录园所，获取园所信息 kgId和kgName
    /**
     * @Query的方式  √√√√√√√
     * 动态指定条件获取信息：@Query
     */
    @GET("MedicalRecordsService.ashx?Option=Login")
    Call<ResponseBody> loginKg(@Query("userName") String userName, @Query("userPwd") String userPwd );


    @GET("MedicalRecordsService.ashx?Option=Login")
    Observable<LoginResult> loginKgRx(@Query("userName") String userName, @Query("userPwd") String userPwd );



    /**
     * 下载头像
     *  http://image.lkyj.com.cn/GardenImage/47/ChildHead/4ca3e1dc-9b94-468c-b423-ec6f6844973f.jpg
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadImgRx(@Url String url);//直接使用网址下载

    // ----------------------------------------------------------------------

    /**
     * √√√√√√√
     * 使用@QueryMap注解可以分别地从Map集合中获取到元素，然后进行逐个的拼接在一起。
     * @param options
     * @return
     */
    @GET("MedicalRecordsService.ashx?Option=Login")
    Call<ResponseBody> loginKg2(@QueryMap Map<String, String> options );


    // POST请求
    /**
     * 携带数据类型为对象时：@Body
     * 注意：以@Body上传参数，会默认加上Content-Type: application/json; charset=UTF-8的请求头，即以JSON格式请求，再以JSON格式响应。
     *
     * @Body用于POST请求，可以将实例对象转换为对应的字符串传递参数
     */
    @POST("MedicalRecordsService.ashx?Option=Login")
    Call<ResponseBody> createUser(@Body LoginUser loginUser);


    /**
     * √√√√√√√
     * 携带数据类型为表单键值对时：@Field
     * @FormUrlEncoded注解来标明这是一个表单请求，然后在我们的请求方法中使用@Field注解来标示所对应的String类型数据的键,从而组成一组键值对进行传递。
     * @FormUrlEncoded是会自动将请求参数的类型调整为表单类型application/x-www-form-urlencoded，不能用于GET请求，如果不用传递参数可以不用加。
     *
     * @Field注解是将每个请求参数都存在请求体中，还可以添加encoded参数，该参数为boolean型，比如：
     * @Field(value = "usename"，encoded = ture) String usename
     * encoded参数为true的话，key-value-pair将会被编码，即将中文和特殊字符进行编码转换。
     */
    @FormUrlEncoded
    @POST("MedicalRecordsService.ashx?Option=Login")
    Call<ResponseBody> createUser2(@Field("userName") String userName, @Field("userPwd") String userPwd);


    // -------------------------------------------------------------------------------------------------
    // 上传文件：
    /**
     * 单文件上传时：@Part
     * 我们需要用@Multipart注解注明，它表示允许多个@Part，
     * @Part则对应的一个RequestBody 对象，
     * RequestBody 则是一个多类型的，当然也是包括文件的
     */
    @Multipart
    @PUT("user/photo")
    Call<ResponseBody> upload(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    // 如：
//    File file = new File(Environment.getExternalStorageDirectory(), "ic_launcher.png");
//    RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file); // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//    RequestBody descriptionRequestBody = RequestBody.create(null, "this is photo.");
//    Call<ResponseBody> call = api.upload(photoRequestBody, descriptionRequestBody);

    // -------------------------------------------------------------------------------------------------
    // 上传文件： https://blog.csdn.net/qq_38998213/article/details/82352104
    @Multipart
    @POST("/UploadProduct.axd")
    Call<ResponseBody> uploadSimpleFile(@Part MultipartBody.Part file);


    /**
     * 多文件上传时：@PartMap
     * 我们需要用@Multipart注解注明，它表示允许多个@Part，
     * 这里其实和单文件上传是差不多的，只是使用一个集合类型的Map封装了文件，并用@PartMap注解来标示起来
     */
    @Multipart
    @PUT("user/photo")
    Call<LoginUser> upload(@PartMap Map<String, RequestBody> photos, @Part("description") RequestBody description);

    //----------------------------------------------------------------------------------------------------
    /**
     * @Header 参考 https://blog.csdn.net/HUandroid/article/details/79883895 此文章
     *   @Headers
     *     Retrofit提供了两种定义Http请求头的参数：静态和动态。静态方法在头部信息初始化的时候已经固定写死了，而动态方法则必须为每个请求单独设置。
     *     静态设置：
     */
    @Headers("LoginUser-Agent: android")
    @POST("login")
    @FormUrlEncoded
    Call<Object> login1(@Field("usename") String usename,
                        @Field("password") String password);

    /**
     * 添加多个header参数也可以，类似数组：
     */
    @Headers({"LoginUser-Agent: android"
            ,"Cache-Control: max-age=640000"})
    @POST("login")
    @FormUrlEncoded
    Call<Object> login2(@Field("usename") String usename,
                        @Field("password") String password);


    /**
     *  @Url:
     *         需求总是变化的，如果需要请求的地址不是以baseUrl开头的话，就需要使用这个注解，直接请求完整的url，忽视baseurl：
     */
    @POST
    @FormUrlEncoded
    Call<Object> login3(@Url String url,
                        @Field("usename") String usename,
                        @Field("password") String password);


    /**
     * @Streaming
     * 大文件下载的时候，同个该注解，防止直接写入内存中。比如app更新时候，下载music的时候。
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

}
