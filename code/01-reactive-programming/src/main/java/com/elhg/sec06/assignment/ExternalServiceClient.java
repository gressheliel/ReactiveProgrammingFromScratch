package com.elhg.sec06.assignment;

import com.elhg.commons.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

public class ExternalServiceClient extends AbstractHttpClient {
    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);

    private Flux<Order> orderFlux;

    public Flux<Order> getOrderFlux() {
        return Objects.isNull(orderFlux) ? orderFlux = getOrderStream(): orderFlux;
    }

    private Flux<Order> getOrderStream() {
        return httpClient
                .get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parseOrder)
                .doOnNext(order -> log.info("Order : {}", order))
                .publish() // Hot Publisher, The subscribers will see the data  if exists a minimum of 'N' subscriber
                .refCount(2);

    }

    private Order parseOrder(String orderStr) {
        String[] parts = orderStr.split(":");
        return new Order(parts[1], Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
    }
}
