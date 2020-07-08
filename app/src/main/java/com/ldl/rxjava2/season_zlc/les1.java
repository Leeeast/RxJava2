package com.ldl.rxjava2.season_zlc;

import java.text.DecimalFormat;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class les1 {
    public static void main(String[] args) {
        test1();
        System.out.println("--------------分割线-------------");
        test2();
        System.out.println("--------------分割线-------------");
        test3();
        try {
            DecimalFormat decimalFormat = new DecimalFormat(",###");
            String stPrice = decimalFormat.format(123456.123);
            System.out.println("stPrice = " + stPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test1() {
        //创建一个上游Observable
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(3);
                emitter.onNext(2);
                emitter.onError(new Throwable("有异常啦"));
                emitter.onNext(4);
                emitter.onComplete();
            }
        });
        //创建一个下游Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError：" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        observable.subscribe(observer);
    }

    //链式调用
    private static void test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(3);
                emitter.onNext(2);
                emitter.onError(new Throwable("有异常啦"));
                emitter.onNext(4);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError：" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    //链式调用
    private static void test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(3);
                emitter.onNext(2);
                emitter.onError(new Throwable("有异常啦"));
                emitter.onNext(4);
                emitter.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("onNext:" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("onError:" + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("onComplete:" + "完成");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("onDisposable:" + disposable.isDisposed());
            }
        });
    }
}
