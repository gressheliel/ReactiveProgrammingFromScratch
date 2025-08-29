package com.elhg.sec05;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec09Timeout_2 {

    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout_2.class);

    public static void main(String[] args) {
        var mono = getProductName()
                .timeout(Duration.ofSeconds(2), fallBack());

        mono
                .timeout(Duration.ofMillis(20))
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
