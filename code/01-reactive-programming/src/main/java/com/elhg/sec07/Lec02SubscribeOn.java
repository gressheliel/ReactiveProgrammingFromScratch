package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOn {
    private static  final Logger log = LoggerFactory.getLogger(Lec02SubscribeOn.class);

    /**
     * doOnNext(item -> log.info("doOnNext {}", item)) registra cada elemento emitido.
     * doFirst(() -> log.info("doFirst 1")) y         //THREAD BOUNDED_ELASTIC
     * doFirst(() -> log.info("doFirst 2"))           //THREAD MAIN
     * ejecutan acciones antes de cualquier otra operación, en orden inverso a como aparecen.
     * El operador subscribeOn(Schedulers.boundedElastic()) indica que la suscripción y la ejecución del flujo ocurrirán en un scheduler elástico, permitiendo que el trabajo se realice en hilos distintos a los principales.
     *
     * Luego, se definen dos Runnable que suscriben al flujo usando un suscriptor por defecto:
     * Runnable runnable1 = () -> flux.subscribe(Util.getDefaultSubscriber());
     * Ambos runnables se ejecutan en hilos separados con Thread.ofPlatform().start(runnable1);, simulando concurrencia.
     *
     */


    public static void main(String[] args) {
        var flux = Flux.create( sink ->{
            for (int i = 0; i < 3; i++) {
                log.info("emitting {}", i);
                sink.next(i);
            }
            sink.complete();
        }).doOnNext(item -> log.info("doOnNext {}", item))
                .doFirst( () -> log.info("doFirst 1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst( () -> log.info("doFirst 2"));

      Runnable runnable1 = () -> flux.subscribe( Util.getDefaultSubscriber() );
        Runnable runnable2 = () -> flux.subscribe( Util.getDefaultSubscriber() );

      Thread.ofPlatform().start(runnable1);
      Thread.ofPlatform().start(runnable2);

      Util.sleepSeconds(2);

    }
}
