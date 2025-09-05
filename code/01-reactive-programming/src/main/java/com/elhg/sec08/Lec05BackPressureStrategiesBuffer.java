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
 * En el código, la estrategia de backpressure onBackpressureBuffer() se utiliza para manejar situaciones
 * en las que el productor genera datos más rápido de lo que el consumidor puede procesar.
 * Cuando el consumidor no puede seguir el ritmo, los elementos emitidos por el productor
 * se almacenan temporalmente en un buffer interno.
 *
 * Por ejemplo, el productor emite elementos cada 50 ms(sleepSeconds(Duration.ofMillis(50));)
 * Mientras tanto, el consumidor procesa cada elemento con una tarea costosa que tarda 1 segundo(Util.sleepSeconds(1);)
 *
 * Como el consumidor es más lento, el buffer evita la pérdida de datos y permite que el flujo continúe sin errores.
 * Sin embargo, si el buffer se llena (por defecto es ilimitado, pero puede configurarse), podrían ocurrir problemas de memoria.
 * Esta estrategia es útil cuando no se quiere perder ningún elemento y se puede tolerar el aumento de memoria temporal.
 */
public class Lec05BackPressureStrategiesBuffer {
    private static final Logger log = LoggerFactory.getLogger(Lec05BackPressureStrategiesBuffer.class);

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
                .onBackpressureBuffer()
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic()) //Consume datos en el boundedElastic
                .map(Lec05BackPressureStrategiesBuffer::timeConsumingTask)
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
