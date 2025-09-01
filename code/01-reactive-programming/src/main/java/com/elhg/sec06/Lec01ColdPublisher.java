package com.elhg.sec06;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec01ColdPublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec01ColdPublisher.class);

    /**
     * Un cold publisher en Project Reactor es un tipo de publisher que genera los datos desde el inicio para cada suscriptor.
     * Esto significa que cada vez que alguien se suscribe, el flujo comienza de nuevo y el suscriptor recibe todos los elementos,
     * independientemente de cuándo se haya suscrito.
     *
     * En el código de Lec01ColdPublisher, se crea un Flux usando Flux.create, que emite los valores 0, 1 y 2 y luego completa.
     * Cada vez que se llama a subscribe, el bloque dentro de Flux.create se ejecuta de nuevo, como lo muestra el log "Invocado...". @param args
     * Por eso, ambos suscriptores (Sub1 y Sub2) reciben todos los elementos desde el principio.
     *
     * Este comportamiento es útil cuando quieres que cada suscriptor reciba la secuencia completa de datos,
     * como en el caso de leer desde una base de datos o procesar una lista de elementos.
     *
     */

    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
            log.info("Invocado...");
            for(int i=0; i<3; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        });

        flux.subscribe(Util.getDefaultSubscriber("Sub1"));
        flux.subscribe(Util.getDefaultSubscriber("Sub2"));

    }
}
