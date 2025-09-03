package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec01DefaultBehaviorDemo {
    private static  final Logger log = LoggerFactory.getLogger(Lec01DefaultBehaviorDemo.class);

    public static void main(String[] args) {
        var flux = Flux.create( sink ->{
            for (int i = 0; i < 3; i++) {
                log.info("emitting {}", i);
                sink.next(i);
            }
            sink.complete();
        }).doOnNext(item -> log.info("doOnNext {}", item));

        /** Si no se define un thread, el flujo se ejecuta en el mismo hilo del suscriptor (main)
         *
         */

        Runnable runnable = () -> flux.subscribe(Util.getDefaultSubscriber("Sub1"));
        Thread.ofPlatform().start(runnable);

    }
}
