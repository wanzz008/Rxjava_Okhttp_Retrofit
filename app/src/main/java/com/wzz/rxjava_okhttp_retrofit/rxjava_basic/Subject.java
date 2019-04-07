package com.wzz.rxjava_okhttp_retrofit.rxjava_basic;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    public List<Observer> list = new ArrayList<>();

    /**
     * 添加一个观察者
     * @param observer
     */
    public void attach(Observer observer){
        System.out.println( "绑定了一个观察者："+observer.getName());
        list.add( observer) ;
    }

    /**
     * 移除一个观察者
     * @param observer
     */
    public void detach(Observer observer){
        System.out.println( "移除了一个观察者："+observer.getName());
        list.remove( observer );
    }

    public void notifyObservers(String state){

        for (Observer observer : list) {
            observer.update( state );
        }
    }
}
