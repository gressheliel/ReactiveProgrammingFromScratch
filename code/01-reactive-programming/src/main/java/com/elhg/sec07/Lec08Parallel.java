package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec08Parallel {
    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Flux.range(1,10)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(Lec08Parallel::process)
                .subscribe(i -> log.info("received {}", i));

        log.info("Total time {}", (System.currentTimeMillis() - start));
        Util.sleepSeconds(7);
    }

    private static int process(int i) {
        log.info("time consuming process for {}", i);
        Util.sleepSeconds(1);
         return i*2;
    }
}
