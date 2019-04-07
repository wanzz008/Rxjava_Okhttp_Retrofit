package com.wzz.rxjava_okhttp_retrofit.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzz.rxjava_okhttp_retrofit.Api;
import com.wzz.rxjava_okhttp_retrofit.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 操作符：
 * Merge 将多个Observables的输出合并
 * concat: 同上
 */
public class MergeActivity extends AppCompatActivity {

    private Api api;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mTextView = findViewById(R.id.text) ;

        String url = "https://www.apiopen.top/" ;
//                "login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 结合使用Rxjava和Retrofit
                .build();

        api = retrofit.create(Api.class);

    }

//    使用 Merge  操作符你可以将多个Observables的输出合并，就好像它们是一个单个的
//    Observable一样。
//    Merge  可能会让合并的Observables发射的数据交错（有一个类似的操作符 Concat  不会让数
//    据交错，它会按顺序一个接着一个发射多个Observables的发射物）。
//    正如图例上展示的，任何一个原始Observable的 onError  通知会被立即传递给观察者，而且
//    会终止合并后的Observable。
    /**
     * Merge : 合并两组数据一起展示（如：本地和网络的数据合并，购物车）
     * @param view
     */
    public void click_map(View view) {

        // Merge
        Observable.merge( getObservable1() , getObservable2())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        for (String string : strings) {
                            Log.d("wzz-----", "onNext: " + string);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("wzz-----", "onComplete..... ");
                    }
                });

        // concat
        Observable.concat(getObservable1(),getObservable2())
                .subscribe(new Consumer<List<String>>() {
                               @Override
                               public void accept(List<String> strings) throws Exception {

                               }
                           }
                );
    }


    private Observable<List<String>> getObservable1(){

        List<String> list = new ArrayList<>();
        list.add("哈哈");
        list.add("嘻嘻");
        return  Observable.just( list ) ;
    }

    private Observable<List<String>> getObservable2(){

        List<String> list = new ArrayList<>();
        list.add("我是Java");
        list.add("我是Python");
        return  Observable.just( list ) ;
    }

}
