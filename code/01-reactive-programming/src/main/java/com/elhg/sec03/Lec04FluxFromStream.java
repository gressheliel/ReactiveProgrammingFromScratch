package com.elhg.sec03;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;


public class Lec04FluxFromStream {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxFromStream.class);

    public static void main(String[] args) {
        var list = List.of("a", "b", "c", "d", "e", "f");
        var stream = list.stream();

        var flux = Flux.fromStream(stream);

        flux.subscribe(Util.getDefaultSubscriber("Sub1"));
        flux.subscribe(Util.getDefaultSubscriber("Sub2")); // Error: stream has already been operated upon or closed

        log.info("---------------------------------------------------------------------------");
        // Solución: crear un nuevo stream para cada suscriptor
        var flux2 = Flux.fromStream(list::stream); // Supplier
        flux2.subscribe(Util.getDefaultSubscriber("Sub1"));
        flux2.subscribe(Util.getDefaultSubscriber("Sub2"));

    }

}
