package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {

    private static final Logger log = LoggerFactory.getLogger(Lec04MonoEmptyError.class);

    public static void main(String[] args) {
        getUsername(1)
                .subscribe(Util.getDefaultSubscriber("mono-ok"));
        getUsername(2)
                .subscribe(Util.getDefaultSubscriber("mono-empty"));
        getUsername(3)
                .subscribe(Util.getDefaultSubscriber("mono-error"));

        // Cuando no se propocina el OnError, manda: Operator called default onErrorDropped
        getUsername(3)
                .subscribe(System.out::println);
    }

    private static Mono<String> getUsername(int userId){
        return switch (userId){
            case 1 -> Mono.just("John");
            case 2 -> Mono.empty();
            default -> Mono.error(new IllegalArgumentException("Invalid user ID"));
        };
    }

}
