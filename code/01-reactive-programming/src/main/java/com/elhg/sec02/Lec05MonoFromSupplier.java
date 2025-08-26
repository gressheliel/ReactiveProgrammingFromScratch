package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec05MonoFromSupplier {

    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    public static void main(String[] args) {
        var list = List.of(1,2,3,4,5);

        Mono.just(sum(list))
                .subscribe(Util.getDefaultSubscriber("mono-list-sum"));

        // Es mejor hacer con un supplier
        // Se retrasa la ejecución hasta que se suscriba
        // y no se ejecuta si no hay suscriptor
        // Se puede comentar el subscribe y ver el funcionamiento
        Mono.fromSupplier(()-> sum(list))
                .subscribe(Util.getDefaultSubscriber("mono-list-sum-supplier"));

    }


    private static int sum(List<Integer> list){
        log.info("Calculating sum of : {}", list);
        return  list
                .stream()
                .reduce(0, Integer::sum);
    }

}
