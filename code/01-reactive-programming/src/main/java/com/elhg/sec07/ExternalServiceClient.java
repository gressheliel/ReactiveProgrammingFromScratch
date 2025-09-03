package com.elhg.sec07;

import com.elhg.commons.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ExternalServiceClient extends AbstractHttpClient {
    /**
     * Recupera los valores del servicio y los entrega a un hilo del scheduler elástico.
     * Esto evita que en el consumo Lec06EventLoopIssueFix se bloquee el event loop del servidor web.
     * Y de vuelva los datos secuencialmente
     */

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);

    public Mono<String> getProductName(int productId) {
        return httpClient
                .get()
                .uri("/demo01/product/"+productId)
                .responseContent()
                .asString()
                .doOnNext(m -> log.info("next: {}", m))
                .next()
                .publishOn(Schedulers.boundedElastic());
    }
}
