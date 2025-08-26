package com.elhg.sec03.assigment;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class MainStock {
    private static final Logger log = LoggerFactory.getLogger(MainStock.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        // Comprar barato (menos de 90) y vender caro (mas de 110)
        // Iniciar con 1000 de balance
        // Comprar 1 unidad cada vez que baje de 90
        // Vender todo cuando suba de 110

        client.getStock()
                .subscribe(new StockPriceSubscriber());


        Util.sleepSeconds(20);
    }
}
