package com.elhg.sec03;

import com.elhg.commons.Util;
import com.elhg.sec03.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {

    private static Logger log = LoggerFactory.getLogger(Lec09FluxInterval.class);

    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(100))
                .map(i -> Util.faker().name().fullName())
                .subscribe(Util.getDefaultSubscriber("flux-interval"));

        Util.sleepSeconds(4);
    }
}
