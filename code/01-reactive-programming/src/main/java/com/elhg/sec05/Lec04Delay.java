package com.elhg.sec05;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {

    private static Logger log = LoggerFactory.getLogger(Lec04Delay.class);

    /*
    * Requerimiento: Emitir del 1 al 10, pero con un delay de 1 segundo entre cada emisión
    * No realiza la generación de los elementos por adelantado
    * Sino que los va generando conforme se van emitiendo
     */
    public static void main(String[] args) {
        Flux.range(1,10)
                .log()
                .delayElements(Duration.ofMillis(100))
                .subscribe(Util.getDefaultSubscriber());

        Util.sleepSeconds(12);
    }

}
