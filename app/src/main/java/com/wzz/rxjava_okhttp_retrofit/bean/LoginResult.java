package com.wzz.rxjava_okhttp_retrofit.bean;

/**
 * Created by Ivan on 2016/10/7.
 */


public class LoginResult {

    public int id ;
    private String key;
    private String phone;
    private String passwd;


    public LoginResult(String key, String phone, String passwd) {
        this.key = key;
        this.phone = phone;
        this.passwd = passwd;
    }


//    @Override
//    public String toString() {
//        return new Gson().toJson(this);
//    }
}
