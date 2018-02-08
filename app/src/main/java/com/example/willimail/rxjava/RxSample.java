package com.example.willimail.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by willimail on 2/8/18.
 */

public class RxSample {
    public static void main(String[] args) {
        printPairs();
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