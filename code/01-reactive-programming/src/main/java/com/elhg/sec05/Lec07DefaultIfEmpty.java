package com.elhg.sec05;

import com.elhg.commons.Util;
import reactor.core.publisher.Mono;

public class Lec07DefaultIfEmpty {
    public static void main(String[] args) {
        Mono.empty()
                .defaultIfEmpty("default")
                .subscribe(Util.getDefaultSubscriber());

        Mono.just("Item")
                .defaultIfEmpty("default")
                .subscribe(Util.getDefaultSubscriber());

    }
}
