package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec10FluxEmptyError {

    private static Logger log = LoggerFactory.getLogger(Lec10FluxEmptyError.class);

    public static void main(String[] args) {
       Flux.empty()
               .subscribe(Util.getDefaultSubscriber());

       Flux.error(new RuntimeException("error occurred"))
               .subscribe(Util.getDefaultSubscriber("error"));
    }
}
