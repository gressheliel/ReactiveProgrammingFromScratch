package com.elhg.sec03;

import com.elhg.commons.Util;
import com.elhg.sec03.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07FluxVsList {

    private static final Logger log = LoggerFactory.getLogger(Lec07FluxVsList.class);

    public static void main(String[] args) {
        // Primer ejemplo: List
        // Se bloque 10 seg. y despues imprime la lista completa
        //var list = NameGenerator.getNamesList(10);
        //log.info("List: {}", list);

        // Segundo ejemplo: Flux
        // No se bloquea, empieza a imprimir los nombres a medida que los va generando
        NameGenerator.getNamesListFlux(10)
                .subscribe(Util.getDefaultSubscriber());
    }
}
