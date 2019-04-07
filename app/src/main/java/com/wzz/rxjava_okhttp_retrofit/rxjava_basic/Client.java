package com.wzz.rxjava_okhttp_retrofit.rxjava_basic;

/**
 * 自己写的观察者
 */
public class Client {

    public static void main(String[] args) {

        // 创建观察者
        Observer observer1 = new ObserverImpl("One");
        Observer observer2 = new ObserverImpl("Two");
        Observer observer3 = new ObserverImpl("Three");

        // 创建被观察者
        MessageSubject messageSubject = new MessageSubject();
        // 绑定观察者
        messageSubject.attach( observer1 );
        messageSubject.attach( observer2 );
        messageSubject.attach( observer3 );

        // 发送消息
        messageSubject.sendMessage("我在跑步锻炼");

        // 移除了一个观察者
        messageSubject.detach( observer1 );
        messageSubject.sendMessage( "我在看书学习");
    }
}
