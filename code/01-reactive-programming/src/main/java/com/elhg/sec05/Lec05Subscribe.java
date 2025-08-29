package com.elhg.sec05;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec05Subscribe {

    private static Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    /**
     *  Es equivalente usar doOnNext, doOnComplete y doOnError
     *  a usar los parametros del subscribe
     *  .subscribe(item -> log.info("Item : {}", item),
     *             error -> log.error("Error: {}", error.getMessage()),
     *             () -> log.info("Completed"));
     */
    public static void main(String[] args) {
        Flux.range(1,10)
                .doOnNext(item -> log.info("Item : {}", item))
                .doOnComplete(()-> log.info("Completed"))
                .doOnError(error -> log.error("Error: {}", error.getMessage()))
                .subscribe();

        Flux.range(1,10)
                .subscribe(
                        item -> log.info("Subscribe Item : {}", item),
                        error -> log.error("Subscribe Error: {}", error.getMessage()),
                        () -> log.info("Subscribe Completed")
                );



    }

}
