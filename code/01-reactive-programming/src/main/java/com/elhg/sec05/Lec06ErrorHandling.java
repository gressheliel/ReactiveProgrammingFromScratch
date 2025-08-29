package com.elhg.sec05;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06ErrorHandling {

    private static Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    /**
     *    How to handle error in a reactive pipeline
     *    Flux.(...)
     *    ...
     *    ...
     *    ...
     *    ...
     */
    public static void main(String[] args) {
       onErrorReturnExample();
       onErrorReturnExample();
       onErrorCompleteExample();
       onErrorContinueExample();
    }

    private static Mono<Integer> fallBackMethod1(){
        log.error("Inside fallBackMethod1");
        return Mono.fromSupplier(()-> Util.faker().random().nextInt(10, 100));
    }

    private static Mono<Integer> fallBackMethod2(){
        log.error("Inside fallBackMethod2");
        return Mono.fromSupplier(()-> Util.faker().random().nextInt(100, 1000));
    }

    private static void onErrorContinueExample(){
        Flux.range(1,10)
                .map(item -> item==5 ? item/0 : item) // ArithmeticException: / by zero
                .onErrorContinue((ex, item) -> log.error("Exception1 {} on item {}", ex.getMessage(), item))
                .subscribe(Util.getDefaultSubscriber());

    }

    private static void onErrorCompleteExample(){
        Mono.error(new RuntimeException("Oops"))
                .onErrorComplete()
                .subscribe(Util.getDefaultSubscriber());
    }

    private static void onErrorResumeExample(){
        Flux.range(1,10)
                .map(item -> item==5 ? item/0 : item) // ArithmeticException: / by zero
                .onErrorResume(ArithmeticException.class, ex -> fallBackMethod1())
                .onErrorResume(error -> fallBackMethod2())
                .onErrorReturn(-4)
                .subscribe(Util.getDefaultSubscriber());

    }
    private static void onErrorReturnExample(){
        Mono.just(5)
                .map(item -> item==5 ? item/0 : item) // ArithmeticException: / by zero
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.getDefaultSubscriber());
    }
}
