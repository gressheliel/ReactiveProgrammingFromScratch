package com.elhg.sec08;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec01BackPressureAutomaticHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec01BackPressureAutomaticHandling.class);

    public static void main(String[] args) {
        //El valor por defecto es 256
        System.setProperty("reactor.bufferSize.small", "16");

        /**
         * El productor es muy rapido y el consumidor es muy lento,
         * se hace uso del backpressure automático.
         *
         * El flujo Inicia en el thread principal,
         * Los datos se emiten en el thread parallel y se consumen en el boundedElastic.
         *
         * Emite 16 elementos, la primera vez, y luego espera a que el consumidor los procese.
         * Cuando se han consumido el 75% de los elementos (12), el productor emite otros 12.
         * Se repite el ciclo. Hasta que se terminan los 60s del hilo principal.
         */

        var producer = Flux.generate(
                ()-> 1,
                (state, sink) -> {
                    log.info("Emitting {}", state);
                    sink.next(state);
                    return ++state;
                },
                (state) -> log.info("Final state: {}", state)
        ).cast(Integer.class).subscribeOn(Schedulers.parallel());  //Emite datos en el thread parallel

        producer
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec01BackPressureAutomaticHandling::timeConsuming)
                .subscribe(Util.getDefaultSubscriber());

        Util.sleepSeconds(60);  // Se bloquea el hilo principal por 60 segundos, fines didácticos.

    }


    private static int timeConsuming(int i) {
        Util.sleepSeconds(1);
        return i;
    }

}
