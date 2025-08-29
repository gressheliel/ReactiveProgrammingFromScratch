package com.elhg.sec05;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Supplier;

public class Lec09Timeout {

    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);

    public static void main(String[] args) {
        getProductName()
                //.timeout(Duration.ofSeconds(1))
                //.onErrorReturn("DEFAULT")
                .timeout(Duration.ofSeconds(2), fallBack())
                .subscribe(Util.getDefaultSubscriber());

        Util.sleepSeconds(4);
    }

    private static Mono<String> getProductName(){
        return Mono.fromSupplier(()-> "From Service :" + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1900));
    }

    private static Mono<String> fallBack(){
       return Mono.fromSupplier(()-> "From fallBack :" + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(300));

    }

}
