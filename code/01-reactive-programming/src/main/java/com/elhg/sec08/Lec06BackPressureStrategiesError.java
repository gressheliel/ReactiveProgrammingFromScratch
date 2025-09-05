package com.elhg.sec08;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Reactor tiene 4 estrategias de backpressure
 * BUFFER (por defecto)
 * DROP
 * LATEST
 * ERROR
 *
 * La estrategia onBackpressureError() en Reactor indica que, si el consumidor no puede procesar
 * los elementos tan rápido como el productor los genera y se produce una situación de backpressure,
 * el flujo emitirá un error inmediatamente en vez de almacenar los elementos en un buffer o descartarlos.
 * Esto es útil cuando no se desea perder datos ni aumentar el uso de memoria, y se prefiere que el sistema falle
 * rápido ante sobrecarga.
 *
 * Por ejemplo, si el productor emite elementos cada 50 ms y el consumidor tarda 1 segundo en procesar cada uno,
 * al no poder seguir el ritmo, se lanzará una excepción y el flujo se detendrá.
 * Esta estrategia es adecuada para sistemas donde la sobrecarga debe ser detectada y gestionada explícitamente.
 */
public class Lec06BackPressureStrategiesError {
    private static final Logger log = LoggerFactory.getLogger(Lec06BackPressureStrategiesError.class);

    public static void main(String[] args) {

         var producer = Flux.create(fluxSink -> {
                    for (int i = 1; i <= 500 && !fluxSink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                        sleepSeconds(Duration.ofMillis(50));
                    }
                    fluxSink.complete();
                }).cast(Integer.class)
                .subscribeOn(Schedulers.parallel());  //Emite datos en el thread parallel

        producer
                .onBackpressureError()
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec06BackPressureStrategiesError::timeConsumingTask)
                .subscribe();

        Util.sleepSeconds(60);  // Se bloquea el hilo principal por 60 segundos, fines didácticos.

    }

    private static int timeConsumingTask(int i) {
        log.info("received {}", i);
        Util.sleepSeconds(1);
        return i;
    }


    private static void sleepSeconds(Duration duration) {
        try {
            Thread.sleep(duration); // Solo JDK 19+
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
