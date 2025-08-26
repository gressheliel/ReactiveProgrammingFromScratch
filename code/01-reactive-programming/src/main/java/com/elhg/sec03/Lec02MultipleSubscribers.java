package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


public class Lec02MultipleSubscribers {

    private static final Logger log = LoggerFactory.getLogger(Lec02MultipleSubscribers.class);

    public static void main(String[] args) {
        var flux = Flux.just(1, 2, 3, 4, 5, 6);

        flux.subscribe(Util.getDefaultSubscriber("Sub1"));
        flux.subscribe(Util.getDefaultSubscriber("Sub2"));
        flux
                .filter(i-> i%2==0)
                .map(i -> i+"A")
                .subscribe(Util.getDefaultSubscriber("Sub3"));
    }

}
