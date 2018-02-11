package com.example.willimail.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by willimail on 2/8/18.
 */

public class RxSample {
    public static void main(String[] args) {
        skipExample();

    }

    private static void skipExample() {
        Observable.from(new String[]{"1", "2", "3", "4", "5", "6"})
                .skip(2)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    private static void listExample() {
        Executor executor =
                new ThreadPoolExecutor(5, 5, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
        List<Integer> values = new ArrayList<>();
        ConnectableObservable observable = Observable
                .just(values)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(executor))
                .publish();
        Action1<List<Integer>> subscriber = new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                System.out.print("New values: ");
                for (Integer i : integers) {
                    System.out.print(i + " ");
                }
                System.out.println("");
            }
        };
        observable.subscribe(subscriber);
        values.add(0);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(2);
        values.add(1);
        values.add(2);
        values.add(1);
        values.add(2);
        values.add(1);
        values.add(2);
        values.add(1);
        values.add(0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.connect();
    }

    private static void flatMap() {
        Observable.just(1, 2, 3, 4, 5)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        //Observables instead of Strings
                        return Observable.just(integer + " - " + integer);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    private static void onCreate() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("Do process 1");
                subscriber.onNext("Do process 2");
                subscriber.onNext("Do process 3");
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error" + e);
            }

            @Override
            public void onNext(Object o) {
                System.out.println(o);
            }
        });
    }

    private static void printPairs() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    private static void concat() {
        Observable<Integer> obsA = Observable.just(1, 2, 3, 4);
        Observable<Integer> obsB = Observable.just(5, 6, 7, 8);

        obsA.concatWith(obsB)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });

        System.out.println("---------------------------");

        Observable.concat(obsB, obsA)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });

        System.out.println("---------------------------");
    }
}