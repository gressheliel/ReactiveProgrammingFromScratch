package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec10MonoDefer {

    private static Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);

    /*Mono.defer()
     - Cuando se requiere crear un Mono, y este consume tiempo en su creación
     - Se puede diferir la creación del Mono hasta que un suscriptor se suscriba a él
     */

    public static void main(String[] args) {
        //createPublisher();                           // Se tardo 2 segundos en crear el publisher
        //Mono.defer(Lec10MonoDefer::createPublisher); // No se tardo nada en crear el publisher

        Mono.defer(Lec10MonoDefer::createPublisher)
                .subscribe(Util.getDefaultSubscriber("mono-from-supplier"));
    }

    // Crea
    private static Mono<Integer> createPublisher(){
        log.info("Creating Publisher...");
        Util.sleepSeconds(2);
        var list = List.of(1,2,3);
        return Mono.fromSupplier(()-> sum(list));
    }

    // Time consuming Business Logic
    private static int sum(List<Integer> list){
        log.info("Calculating sum of list {}", list);
        Util.sleepSeconds(3);
        return list.stream().reduce(0, Integer::sum);
    }

}
