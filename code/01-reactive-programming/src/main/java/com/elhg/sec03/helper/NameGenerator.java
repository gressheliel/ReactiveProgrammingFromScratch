package com.elhg.sec03.helper;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

public class NameGenerator {

    public static List<String> getNamesList(int count){
        return IntStream.range(1, count)
                .mapToObj(f -> generateName())
                .toList();
    }


    public static Flux<String> getNamesListFlux(int count){
        return Flux.range(1, count)
                .map(i -> generateName());
    }

    private static  String generateName(){
        Util.sleepSeconds(1);
        return Util.faker().name().fullName();
    }

}
