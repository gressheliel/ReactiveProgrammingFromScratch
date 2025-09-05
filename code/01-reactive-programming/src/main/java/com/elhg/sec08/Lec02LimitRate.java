package com.elhg.sec08;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec02LimitRate {
    private static final Logger log = LoggerFactory.getLogger(Lec02LimitRate.class);

    public static void main(String[] args) {
          /**
         * El productor es muy rápido y el consumidor es muy lento,
         * Hey producer, soy un consumidor lento, aunque te pida todos los datos,
         * solo dame lo del rateLimiter(5)....
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
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec02LimitRate::timeConsuming)
                .subscribe(Util.getDefaultSubscriber());

        Util.sleepSeconds(60);  // Se bloquea el hilo principal por 60 segundos, fines didácticos.

    }

    private static int timeConsuming(int i) {
        Util.sleepSeconds(1);
        return i;
    }
}

