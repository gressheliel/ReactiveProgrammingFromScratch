package com.elhg.sec06;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04HotPublisherCache {
    private static final Logger log = LoggerFactory.getLogger(Lec04HotPublisherCache.class);

    public static void main(String[] args) throws InterruptedException {
        // Recibe los datos al momento de conectarse, pero no los que se emitieron antes de conectarse
       /*var stockFlux = stockStream()
                .publish()
               .autoConnect(0);*/

        // Guarda los 'N' valor(es) emitido(s) y se lo envía a los nuevos suscriptores
        var stockFlux = stockStream()
                .replay(1)
                .autoConnect(0);

        Util.sleepSeconds(4);
        log.info("Sam is joining");
        stockFlux
                 .subscribe(Util.getDefaultSubscriber("Sam"));

        Util.sleepSeconds(3);
        log.info("Mike is joining");
        stockFlux
                .subscribe(Util.getDefaultSubscriber("Mike"));

        Util.sleepSeconds(15);
    }


    //Stock
    private static Flux<Integer> stockStream(){
        return Flux.generate(sink -> sink.next(Util.faker().random().nextInt(10,100)))
                .delayElements(Duration.ofSeconds(3))
                .doOnNext(item -> log.info("Emitting {}", item))
                .cast(Integer.class);
    }
}
