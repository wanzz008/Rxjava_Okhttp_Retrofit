package com.wzz.rxjava_okhttp_retrofit.bean;

/**
 * Created by Ivan on 2016/10/7.
 */


public class LoginParam {

    public String key;
    public String phone;
    public String passwd;


    public LoginParam(String key, String phone, String passwd) {
        this.key = key;
        this.phone = phone;
        this.passwd = passwd;
    }
    public LoginParam() {

    }


//    @Override
//    public String toString() {
//        return new Gson().toJson(this);
//    }
}
