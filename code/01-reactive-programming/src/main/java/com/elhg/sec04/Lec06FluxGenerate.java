package com.elhg.sec04;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    public static void main(String[] args) {

        //Flux.create( Consumer<FluxSink<T>> )
        //Flux.generate( Consumer<SynchronousSink<T>> )l

        // Emitir un elemento a la vez
        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            //synchronousSink.next(2);   // Solo es posible emitir un elemento
            synchronousSink.complete();
        }).subscribe(Util.getDefaultSubscriber());


        // Emitir varios elementos a la vez
        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            //synchronousSink.next(2);   // Solo es posible emitir un elemento
            //synchronousSink.complete(); // Emite de manera indefinida
        }).take(3)
                .subscribe(Util.getDefaultSubscriber());

        // Emitir mientras se cumpla una condicion
        Flux.generate(synchronousSink -> {
            var country = Util.faker().country().name();
            log.info("Emitting: {}", country);
            synchronousSink.next(country);
        }).takeWhile(item -> !item.equals("Canada"))
                .subscribe(Util.getDefaultSubscriber());


    }
}
