package com.elhg.sec08;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribers {
    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribers.class);

    public static void main(String[] args) {

        /**
         * Un productor muy rápido y dos consumidores (uno lento y otro rápido)
         * El productor puede ajustar la velocidad en función de la demanda de cada consumidor
         *
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
                .limitRate(5)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic, Thread slow
                .map(Lec03MultipleSubscribers::timeConsuming)
                .subscribe(Util.getDefaultSubscriber("sub1"));

        producer
                .take(100)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic, Thread Fast
                .subscribe(Util.getDefaultSubscriber("sub2"));


        Util.sleepSeconds(60);  // Se bloquea el hilo principal por 60 segundos, fines didácticos.

    }

    private static int timeConsuming(int i) {
        Util.sleepSeconds(1);
        return i;
    }
}
