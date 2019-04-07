package com.wzz.rxjava_okhttp_retrofit.rxjava_basic;

public class MessageSubject extends Subject{

    public void sendMessage(String message){
        notifyObservers( message );
    }

}
