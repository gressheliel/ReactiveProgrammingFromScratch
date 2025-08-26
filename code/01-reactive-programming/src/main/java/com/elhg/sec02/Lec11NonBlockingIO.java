package com.elhg.sec02;

import com.elhg.commons.Util;
import com.elhg.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec11NonBlockingIO {
    private static Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        log.info("Starting ...");

        for(int i=1; i<=50; i++) {
            // No usar block() en un entorno reactivo, para los tests está bien
            /*var name = client.getProductName(i)
                    .block();
            log.info("Product name: {}", name);*/

            client.getProductName(i)
                .subscribe(name -> log.info("Product name: {}", name));
        }

        // Para ver la respuesta, se debe bloquera el hilo principal
        Util.sleepSeconds(2);
    }
}
