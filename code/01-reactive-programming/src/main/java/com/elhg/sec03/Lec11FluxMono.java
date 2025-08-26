package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec11FluxMono {

    private static Logger log = LoggerFactory.getLogger(Lec11FluxMono.class);

    public static void main(String[] args) {
        var mono = getUserName(1);
        Flux.from(mono)
                .map(String::toUpperCase)
                .subscribe(Util.getDefaultSubscriber("mono"));
    }

    private static Mono<String> getUserName(int userId) {
        return switch (userId){
            case 1 -> Mono.just("John");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Not in the allowed range 1-2"));
        };
    }

    private static void save (Flux<String> flux){
        flux.subscribe(Util.getDefaultSubscriber("save"));
    }
}
