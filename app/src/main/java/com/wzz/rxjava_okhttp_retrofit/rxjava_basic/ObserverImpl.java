package com.wzz.rxjava_okhttp_retrofit.rxjava_basic;

public class ObserverImpl implements Observer{

    private String TAG = "wzz-----";

    private String name ;
    public ObserverImpl(String name) {
        this.name = name;
    }

    @Override
    public void update(String state) {
        System.out.println( name + " -->我收到通知了..." + state );
    }

    @Override
    public String getName() {
        return name;
    }
}
