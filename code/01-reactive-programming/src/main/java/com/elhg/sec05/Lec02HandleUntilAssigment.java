package com.elhg.sec05;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssigment {
    /*
       Emitir países hasta que encuentre a Canada
     */

    private static Logger log = LoggerFactory.getLogger(Lec02HandleUntilAssigment.class);

    public static void main(String[] args) {
        Flux.<String>generate(synchronousSink -> {
            String country = Util.faker().country().name();
            log.info("Emitiendo: {}", country);
            synchronousSink.next(country);
        }).handle( (country, sink) -> {
            if (country.equalsIgnoreCase("Canada")) {
                sink.complete();
            } else {
                sink.next(country);
            }
        }).cast(String.class)
                .subscribe(Util.getDefaultSubscriber());
    }
}
