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

 */
public class Lec06BackPressureStrategiesDrop {
    private static final Logger log = LoggerFactory.getLogger(Lec06BackPressureStrategiesDrop.class);

    /**
     * La estrategia onBackpressureDrop() en Reactor indica que, cuando el productor genera elementos más rápido
     * de lo que el consumidor puede procesar, los elementos excedentes se descartan (no se almacenan ni se procesan).
     * Esto evita el uso excesivo de memoria y permite que el flujo continúe sin errores, pero implica pérdida de datos.
     * Produce cada 50ms y consume cada 1 segundo.  Produce 20 elementos por segundo y consume 1 elemento por segundo.
     * onNext(1), onNext(2), onNext(22), onNext(42), onNext(62), onNext(82), onNext(102)...
     * Los demás elementos se descartan si el consumidor no puede seguir el ritmo.
     *
     */

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
                .onBackpressureDrop()
                .log()
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec06BackPressureStrategiesDrop::timeConsumingTask)
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
