package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec05PublishOn {

    /**
     * subscribeOn
     * Este operador afecta el hilo donde se inicia la suscripción y toda la cadena de operadores aguas abajo.
     * Si lo colocas al principio, todo el flujo se ejecuta en el scheduler indicado,
     * desde la generación de datos hasta el procesamiento.
     *
     * publishOn
     * Este operador cambia el hilo de ejecución solo a partir del punto donde se coloca en la cadena.
     * Los operadores anteriores siguen ejecutándose en el hilo original, y los posteriores se mueven al scheduler especificado.
     * Solo los operadores después de publishOn se ejecutan en el scheduler elástico; los anteriores no cambian de hilo.
     *
     * Resumen:
     * subscribeOn: afecta todo el flujo desde el inicio.
     * publishOn: solo afecta los operadores posteriores a su posición.
     */

    private static final Logger log = LoggerFactory.getLogger(Lec05PublishOn.class);

    public static void main(String[] args) {
        var flux = Flux.create(sink ->{
                    for (int i = 0; i < 3; i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .doOnNext(item -> log.info("value {}", item))
                .doFirst( () -> log.info("first1"))
                .publishOn(Schedulers.boundedElastic())
                .doFirst( () -> log.info("first2"));

        Runnable runnable1 = () -> flux.subscribe( Util.getDefaultSubscriber("sub1") );
        Thread.ofPlatform().start(runnable1);

        Util.sleepSeconds(2);
    }
}
