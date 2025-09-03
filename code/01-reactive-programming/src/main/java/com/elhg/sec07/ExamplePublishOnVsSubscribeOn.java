package com.elhg.sec07;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ExamplePublishOnVsSubscribeOn {
    /**
     *  // publishOn() -> cambia el Scheduler en el punto donde se invoca
     *  // subscribeOn() -> cambia el Scheduler en el punto donde se suscribe, afecta a toda la cadena
     *
     */

    public static void main(String[] args) {
        System.out.println("Inicio Main Thread: " + Thread.currentThread().getName());
        examplePublishOn();
        Util.sleepSeconds(1);
        exampleSubscribeOn();
    }

    private static void exampleSubscribeOn() {
        System.out.println("******** exampleSubscribeOn *********");
        Flux.range(1, 2)
                .map(i -> {
                    System.out.println(String.format("First map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    System.out.println(String.format("Second map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                    return i;
                }).subscribe();
    }


    private static void examplePublishOn() {
        System.out.println("******** examplePublishOn *********");
        Flux.range(1, 2)
                .map(i -> {
                    System.out.println(String.format("First map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    System.out.println(String.format("Second map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                    return i;
                }).subscribe();

    }

}
