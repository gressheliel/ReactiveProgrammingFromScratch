package com.elhg.sec07;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec07PublishOnSubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(Lec07PublishOnSubscribeOn.class);

    public static void main(String[] args) {
        var flux = Flux.create(sink ->{
                    for (int i = 0; i < 3; i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .publishOn(Schedulers.parallel())
                .doOnNext(item -> log.info("value {}", item))
                .doFirst( () -> log.info("first1"))
                .publishOn(Schedulers.boundedElastic())
                .doFirst( () -> log.info("first2"));

        Runnable runnable1 = () -> flux.subscribe( Util.getDefaultSubscriber("sub1") );
        Thread.ofPlatform().start(runnable1);

        Util.sleepSeconds(2);
    }
}
