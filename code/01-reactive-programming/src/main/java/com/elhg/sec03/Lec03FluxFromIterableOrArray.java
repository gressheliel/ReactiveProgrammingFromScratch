package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;


public class Lec03FluxFromIterableOrArray {

    private static final Logger log = LoggerFactory.getLogger(Lec03FluxFromIterableOrArray.class);

    public static void main(String[] args) {
        var list = List.of("a", "b", "c", "d");
        Flux.fromIterable(list)
                .subscribe(Util.getDefaultSubscriber());

        Integer[] arr = {1, 2, 3, 4, 5};
        Flux.fromArray(arr)
                .subscribe(Util.getDefaultSubscriber());

    }

}
