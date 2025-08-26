package com.elhg.sec02;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;


public class Lec08MonoFromCompletableFuture {

    private static Logger log = LoggerFactory.getLogger(Lec08MonoFromCompletableFuture.class);


    public static void main(String[] args) {

        /**
         * CompletableFuture es Eager
         * Cuando se comenta el .subscribe() se observa la diferencia entre:
         * Mono.fromFuture(getData())      :  Se ejecuta inmediatamente y no espera a que se complete el CompletableFuture.
         * Mono.fromFuture(()->getData())  :  Se ejecuta cuando se suscribe, es decir, cuando se llama a .subscribe(). RECOMENDADO
         *
         * fromCallable: acepta una función que puede lanzar excepciones checked (Callable<T>).
         * Útil si tu lógica puede fallar y necesitas manejar excepciones.
         * fromSupplier: acepta una función que no lanza excepciones checked (Supplier<T>).
         * Solo produce un valor, sin manejo de excepciones checked.
         * fromRunnable: acepta una tarea sin valor de retorno ni excepciones checked (Runnable).
         * El Mono emitirá Void al completarse.
         * fromFuture: acepta un CompletableFuture<T>.
         * Convierte el resultado asíncrono de un CompletableFuture en un Mono.
         *
         * Recomendaciones:
         * Usa fromCallable si tu lógica puede lanzar excepciones.
         * Usa fromSupplier si solo necesitas devolver un valor sin excepciones.
         * Usa fromRunnable para ejecutar una acción sin devolver valor.
         * Usa fromFuture para integrar código asíncrono basado en CompletableFuture.
         */


        Mono.fromFuture(()->getData())
                .subscribe(Util.getDefaultSubscriber("mono-from-completable-future"));

        Util.sleepSeconds(1);
    }

    private static  CompletableFuture<String> getData() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching data...");
            return Util.faker().name().fullName();
        });
    }
}
