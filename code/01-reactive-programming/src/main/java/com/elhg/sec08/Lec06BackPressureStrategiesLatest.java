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
public class Lec06BackPressureStrategiesLatest {
    private static final Logger log = LoggerFactory.getLogger(Lec06BackPressureStrategiesLatest.class);

    /**
     * La estrategia onBackpressureLatest() en Reactor gestiona el backpressure manteniendo
     * solo el elemento más reciente cuando el consumidor no puede seguir el ritmo del productor.
     * Si el productor emite datos más rápido de lo que el consumidor los procesa, los elementos intermedios
     * se descartan y solo el último valor pendiente se entrega al consumidor cuando esté listo.
     *
     * En el código, el productor genera elementos cada 50 ms, mientras que el consumidor tarda 1 segundo por elemento.
     * Con onBackpressureLatest(), si el consumidor está ocupado, los valores generados en ese intervalo se descartan
     * y solo el más reciente se procesa. Esto es útil cuando solo interesa el dato más actual y se puede tolerar
     * la pérdida de información intermedia.
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
                .onBackpressureLatest()
                .log()
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec06BackPressureStrategiesLatest::timeConsumingTask)
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
