package com.elhg.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {
    private static final Logger log = LoggerFactory.getLogger(Lec08SwitchIfEmpty.class);

    public static void main(String[] args) {
        Flux.range(1,10)
                .filter(i -> i > 10)
                .switchIfEmpty(fallback())
                .subscribe(i -> log.info("Item: {}", i));
    }

    private static Flux<Integer> fallback() {
        log.info("Generating fallback");
        return Flux.range(100, 5);
    }
}
