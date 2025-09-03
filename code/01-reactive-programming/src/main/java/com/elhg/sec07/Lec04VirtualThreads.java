package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec04VirtualThreads {
    private static final Logger log = LoggerFactory.getLogger(Lec04VirtualThreads.class);

    public static void main(String[] args) {
        /**
         * Establece una propiedad del sistema que indica a Reactor que utilice hilos virtuales
         * para el scheduler boundedElastic. Esto permite que las tareas que normalmente
         * se ejecutarían en hilos tradicionales usen hilos virtuales, mejorando la eficiencia
         * y escalabilidad en aplicaciones reactivas que requieren muchas operaciones concurrentes.
         */
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(sink ->{
                    for (int i = 0; i < 3; i++) {
                        log.info("emitting {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .subscribeOn(Schedulers.newParallel("PARALLEL-1"))
                .doOnNext(item -> log.info("doOnNext {}", item))
                .doFirst( () -> log.info("doFirst 1, isVirtualThread ? : {}", Thread.currentThread().isVirtual()))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst( () -> log.info("doFirst 2"));

        Runnable runnable1 = () -> flux.subscribe( Util.getDefaultSubscriber("Sub1") );
        Thread.ofPlatform().start(runnable1);

        Util.sleepSeconds(2);
    }
}
