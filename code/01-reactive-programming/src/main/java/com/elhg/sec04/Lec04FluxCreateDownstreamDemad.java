package com.elhg.sec04;

import com.elhg.commons.Util;
import com.elhg.sec01.SubscriberImpl;
import com.elhg.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class Lec04FluxCreateDownstreamDemad {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemad.class);

    public static void main(String[] args) {
        //produceEarly();
        produceOnDemand();
    }



    private static void produceOnDemand(){
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            fluxSink.onRequest(request -> {
                log.info("Received request: {}", request);
                for(int i=0; i<request && !fluxSink.isCancelled() ; i++) {
                    var name = Util.faker().name().fullName();
                    log.info("Emitting On Demand: {}", name);
                    fluxSink.next(name);
                }
            });
      }).subscribe(subscriber);


        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);

        subscriber.getSubscription().cancel();

        // ya no se pueden pedir más elementos
        subscriber.getSubscription().request(2);
    }

    private static void  produceEarly(){
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            for(int i=0; i<10; i++) {
                var name = Util.faker().name().fullName();
                log.info("Emitting: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);
        // Hasta aqui se han generado los elementos
        // La conducta por default es emitir todos los elementos
        // pero el subscriber puede pedir de a n elementos


        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);

        subscriber.getSubscription().cancel();

        // ya no se pueden pedir más elementos
        subscriber.getSubscription().request(2);
    }

}
