package com.elhg.sec04;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Flux.generate(synchronousSink -> {
            var country = Util.faker().country().name();
            if(country.equalsIgnoreCase("canada") || atomicInteger.get() == 10) {
                synchronousSink.complete();
            }
            synchronousSink.next(country);
            atomicInteger.incrementAndGet();
        }).subscribe(Util.getDefaultSubscriber());
    }
}
