package com.elhg.sec08;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Lec04FluxCreate {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreate.class);

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16"); // Valor por defecto 256

        /**
         * Flux.generate, Gestiona de manera automática la demanda (backpressure)
         * Flux.create, No gestiona de manera automática la demanda (backpressure), hay que hacerlo manualmente.
         *
         * Cuando el consumidor es demasiado lento y el productor muy rapido, entonces
         * particularmente con Flux.create, no hay un manejo automatico del backpressure
         *
         */
        var producer = Flux.create(fluxSink -> {
                    for (int i = 1; i <= 500 && !fluxSink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                        sleepSeconds(Duration.ofMillis(50));
                    }
                    fluxSink.complete();
                }).cast(Integer.class)
                .subscribeOn(Schedulers.parallel());  //Emite datos en el thread parallel

        producer
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec04FluxCreate::timeConsuming)
                .subscribe();

        Util.sleepSeconds(60);  // Se bloquea el hilo principal por 60 segundos, fines didácticos.

    }

    private static int timeConsuming(int i) {
        log.info("received {}", i);
        Util.sleepSeconds(1);
        return i;
    }


    private static void sleepSeconds(Duration duration) {
        try {
            Thread.sleep(duration); // Solo JDK 19+
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
