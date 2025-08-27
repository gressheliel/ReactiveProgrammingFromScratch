package com.elhg.sec04;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithStateRefactor {
    public static void main(String[] args) {

        // Emitir hasta 10 elementos o hasta que salga canada, lo que ocurra primero
        Flux.generate(
                () -> 0, // initial state
                (state, synchronousSink) -> { // state + sink
                    var country = Util.faker().country().name();
                    if(country.equalsIgnoreCase("canada") || state == 10) {
                        synchronousSink.complete();
                    }
                    synchronousSink.next(country);

                    return ++state; // new state
                }
        ).subscribe(Util.getDefaultSubscriber());

    }
}
