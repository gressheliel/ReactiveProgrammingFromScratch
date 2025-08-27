package com.elhg.sec04;

import com.elhg.commons.Util;
import com.elhg.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class Lec03FluxSinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        //NO thread safe
        demo1();

        //Thread safe
        demo2();
    }


    private static void demo1() {
        var list = new ArrayList<Integer>();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for(int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3);
        log.info("Not Thread safe Should be 10000, but are {}", list.size());
    }

    private static void demo2() {
        var list = new ArrayList<String>();
        var generator = new NameGenerator();


        Flux.create(generator)
                .subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                generator.generate();
            }
        };

        for(int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3);
        log.info("Thread Safe Should be 10000, but are {}", list.size());
    }

}
