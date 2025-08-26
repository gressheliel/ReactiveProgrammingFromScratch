package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;



public class Lec01FluxJust {

    private static final Logger log = LoggerFactory.getLogger(Lec01FluxJust.class);

    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4, "Hola")
                .subscribe(Util.getDefaultSubscriber());
    }

}
