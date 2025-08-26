package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec06MonoFromCallable {

    private static final Logger log = LoggerFactory.getLogger(Lec06MonoFromCallable.class);

    public static void main(String[] args) {
        var list = List.of(1,2,3,4,5);

        // Mono.fromCallable no se ejecuta hasta que se suscribe
        /**
         * fromCallable acepta una función que puede lanzar excepciones (Callable<T>),
         * útil si el método puede lanzar checked exceptions.
         * fromSupplier acepta una función que no lanza checked exceptions (Supplier<T>),
         * más simple si no hay excepciones.
         */
        Mono.fromCallable(() -> sum(list))
                .subscribe(Util.getDefaultSubscriber("mono-list-sum-callable"));

    }


    private static int sum(List<Integer> list){
        log.info("Calculating sum of : {}", list);
        return  list
                .stream()
                .reduce(0, Integer::sum);
    }

}
