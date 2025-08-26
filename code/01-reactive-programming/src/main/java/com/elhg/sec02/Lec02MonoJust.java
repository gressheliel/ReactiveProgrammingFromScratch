package com.elhg.sec02;

import com.elhg.sec01.SubscriberImpl;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec02MonoJust {

    private static final Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {
        Mono<String> mono = Mono.just("Hello, Mono!");
        log.info("Mono creado: {}", mono);

        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        subscriber.getSubscription().request(10);

        ejemplo(Mono.just("Ejemplo de Mono Just"));
        ejemplo(Flux.fromIterable(List.of("Elemento 1", "Elemento 2", "Elemento 3")));
    }

    private static void ejemplo(Publisher<String> message) {
    }
}
