package com.elhg.sec04;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {

    private static  final Logger log = LoggerFactory.getLogger(Lec01FluxCreate.class);

    public static void main(String[] args) {

        // Flux.create recibe como parametro un : Consumer<FluxSink<T>>

        Flux.create(fluxSink -> {
            boolean continueEmitting = true;

            while(continueEmitting){
                var country = Util.faker().country().name();
                log.info("Emitting: {}", country);
                if(country.equalsIgnoreCase("Sweden")) {
                    fluxSink.error(new RuntimeException("I don't like Sweden"));
                    continueEmitting = false;
                }
                fluxSink.next(Util.faker().country().name());
            }
            fluxSink.complete();
        }).subscribe(Util.getDefaultSubscriber("fluxSink"));
    }
}
