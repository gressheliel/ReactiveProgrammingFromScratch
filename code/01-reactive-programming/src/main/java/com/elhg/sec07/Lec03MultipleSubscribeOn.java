package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribeOn {
    private static  final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribeOn.class);

    /** Multiples Subscribers
     * Pueden existir multiples subscribeOn en un pipeline, pero el mas cercano
     * al productor es el que tiene prioridad y el que se utiliza.
     * Schedulers.immediate() es un scheduler en Project Reactor que ejecuta las tareas de manera inmediata en el hilo actual,
     * sin cambiar de hilo ni usar concurrencia. Es útil para pruebas o cuando no se necesita asincronía.
     * Por ejemplo, si usas: .subscribeOn(Schedulers.immediate())
     * todo el pipeline se ejecutará en el mismo hilo donde se realizó la suscripción.
     * sin delegar a otros hilos ni thread pools.
     */


    public static void main(String[] args) {
        var flux = Flux.create(sink ->{
                    for (int i = 0; i < 3; i++) {
                        log.info("emitting {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .subscribeOn(Schedulers.newParallel("PARALLEL-1"))
                .doOnNext(item -> log.info("doOnNext {}", item))
                .doFirst( () -> log.info("doFirst 1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst( () -> log.info("doFirst 2"));

        Runnable runnable1 = () -> flux.subscribe( Util.getDefaultSubscriber("Sub1") );
        Thread.ofPlatform().start(runnable1);

        Util.sleepSeconds(2);
    }
}
