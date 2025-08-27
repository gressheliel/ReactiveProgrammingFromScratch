package com.elhg.sec04;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {
    public static void main(String[] args) {
       //demo1();
       demo2();
    }


    private static void demo2(){
        Flux.<String>generate(synchronousSink -> {
            var country = Util.faker().country().name();
            synchronousSink.next(country);
        }).takeUntil(country -> country.equalsIgnoreCase("Canada"))
                .subscribe(Util.getDefaultSubscriber());
    }


    private static void demo1(){
        Flux.generate(synchronousSink -> {
            var country = Util.faker().country().name();
            if(!country.equalsIgnoreCase("canada")){
                synchronousSink.complete();
            }
            synchronousSink.next(country);
        }).subscribe(Util.getDefaultSubscriber());
    }
}
