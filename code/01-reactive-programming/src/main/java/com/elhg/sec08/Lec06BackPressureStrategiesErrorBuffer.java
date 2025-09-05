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
public class Lec06BackPressureStrategiesErrorBuffer {
    private static final Logger log = LoggerFactory.getLogger(Lec06BackPressureStrategiesErrorBuffer.class);

    /**
     * La línea seleccionada aplica la estrategia de backpressure onBackpressureBuffer con un tamaño de buffer de 10 elementos.
     * Esto significa que, si el consumidor no puede procesar los datos tan rápido como el productor los genera,
     * los elementos excedentes se almacenan en un buffer de hasta 10 posiciones.
     * Si el buffer se llena (al intentar agregar el elemento número 12, ya que el primero se consume inmediatamente),
     * se lanza una excepción y el flujo se detiene. Esta estrategia permite manejar picos de producción temporalmente,
     * pero limita el uso de memoria y detecta sobrecarga rápidamente.
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
                .onBackpressureBuffer(10) // Guarda del 2-11, en el 12 lanza error
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec06BackPressureStrategiesErrorBuffer::timeConsumingTask)
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
