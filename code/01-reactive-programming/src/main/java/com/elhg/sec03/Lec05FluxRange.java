package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;


public class Lec05FluxRange {

    private static final Logger log = LoggerFactory.getLogger(Lec05FluxRange.class);

    public static void main(String[] args) {
       Flux.range(3, 10)
               .subscribe(Util.getDefaultSubscriber());


       Flux.range(1, 10)
               .map(i -> Util.faker().name().fullName())
               .subscribe(Util.getDefaultSubscriber());

    }

}
