package com.elhg.sec04;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;

public class Lec05TakeOperator {

    public static void main(String[] args) {
        take();       // Solo emite los primeros 3
        takeWhile();  // Solo emite mientras la condicion sea true
        takeUntil();  // Solo emite uno
    }

    private static void takeUntil(){
        Flux.range(1, 10)
                .log("takeUntil")
                .takeUntil(i -> i < 3) // stop when the condition is met  +
                .log("Subscribe takeUntil")
                .subscribe(Util.getDefaultSubscriber());
    }


    private static void takeWhile(){
        Flux.range(1, 10)
                .log("takeWhile")
                .takeWhile(i -> i < 3) // takeWhile takes elements while the condition is true
                .log("Subscribe takeWhile")
                .subscribe(Util.getDefaultSubscriber());
    }


    private static void take(){
        Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("Subscribe take")
                .subscribe(Util.getDefaultSubscriber());
    }
}
