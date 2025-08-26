package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


public class Lec06Log {

    private static final Logger log = LoggerFactory.getLogger(Lec06Log.class);

    public static void main(String[] args) {
        Flux.range(1, 3)
                .log("Range -> toMap")// operador log
                .map(i -> Util.faker().name().fullName())
                .log("map -> toSubscribe") // operador log
                .subscribe(Util.getDefaultSubscriber());

    }


}
