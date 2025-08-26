package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec09PublisherCreateVsExecute {
    private static Logger log = LoggerFactory.getLogger(Lec09PublisherCreateVsExecute.class);

    public static void main(String[] args) {
        getName()
                .subscribe(Util.getDefaultSubscriber("mono-from-supplier"));

        /* getName() : Crea el Mono pero no ejecuta la lógica interna hasta que alguien se suscribe.
         * Si no hay suscripción, la lógica interna no se ejecuta.
         * Esto es útil para evitar trabajo innecesario si nadie está interesado en el resultado.
         */
    }

    public static Mono<String> getName(){
        log.info("Inside the method Creating Mono...");
        return Mono.fromSupplier(()->{
            log.info("Generating name...");
            Util.sleepSeconds(2);
            return Util.faker().name().fullName();
        });
    }
}
