package com.elhg.sec02;

import com.elhg.commons.DefaultSubscriber;
import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {

    private static final Logger log = LoggerFactory.getLogger(Lec03MonoSubscribe.class);

    public static void main(String[] args) {
        var mono = Mono.just(1);

        mono.subscribe(
                i-> log.info("received: {}", i),
                err -> log.error("error: {}", err.getMessage()),
                () -> log.info("completed"),
                sub -> sub.request(1));

        var monoZero = Mono.just(1)
                .map(i -> i / 0 );
        monoZero.subscribe(
                i-> log.info("received: {}", i),
                err -> log.error("error: {}", err.getMessage()),
                () -> log.info("completed"),
                sub -> sub.request(1));

        var monoDefaultSubscriber = Mono.just(1);
        monoDefaultSubscriber.subscribe(Util.getDefaultSubscriber("monoDefaultSubscriber"));

    }

}
