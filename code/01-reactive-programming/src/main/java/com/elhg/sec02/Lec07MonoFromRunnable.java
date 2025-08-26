package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec07MonoFromRunnable {

    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {

        /**
         * fromCallable: acepta una función que puede lanzar excepciones checked (Callable<T>).
         * Útil si el método puede fallar.
         * fromSupplier: acepta una función que no lanza excepciones checked (Supplier<T>).
         * Solo produce un valor.
         * fromRunnable: acepta una tarea sin valor de retorno ni excepciones checked (Runnable).
         * El Mono emitirá Void al completarse.
         *
         * Resumen:
         *
         * Usa fromCallable si tu lógica puede lanzar excepciones.
         * Usa fromSupplier si solo necesitas devolver un valor sin excepciones.
         * Usa fromRunnable para ejecutar una acción sin devolver valor.
         *
         */
        getProductName(5)
                .subscribe(Util.getDefaultSubscriber("mono-from-runnable"));

    }


    private static Mono<String> getProductName(int productId) {
        if (productId == 1) {
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static  void notifyBusiness(int productId) {
        log.info("Notifying business for unavailable productId: {}", productId);
    }
}
