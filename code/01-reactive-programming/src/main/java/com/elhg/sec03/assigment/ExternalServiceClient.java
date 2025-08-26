package com.elhg.sec03.assigment;

import com.elhg.commons.AbstractHttpClient;
import reactor.core.publisher.Flux;


public class ExternalServiceClient extends AbstractHttpClient {
    public Flux<Integer> getStock() {
        return httpClient
                .get()
                .uri("/demo02/stock/stream")
                .responseContent()
                .asString()
                .map(Integer::parseInt);
    }
}
