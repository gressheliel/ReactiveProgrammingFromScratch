package com.elhg.sec04;

import com.elhg.commons.Util;
import com.elhg.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {

    private static  final Logger log = LoggerFactory.getLogger(Lec02FluxCreateRefactor.class);

    public static void main(String[] args) {
        NameGenerator nameGenerator = new NameGenerator();


        Flux.create(nameGenerator)
                .subscribe(Util.getDefaultSubscriber("fluxSink"));


        for(int i = 0; i < 10; i++) {
            nameGenerator.generate();
        }

    }
}
