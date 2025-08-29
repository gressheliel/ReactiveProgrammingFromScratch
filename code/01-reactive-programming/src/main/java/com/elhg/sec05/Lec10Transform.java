package com.elhg.sec05;

import com.elhg.commons.Util;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Lec10Transform {

    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);
    record Customer(int id, String name) {}
    record PurchaseOrder(String productName, int price, int quantity) {}

    /**
     * Transform, permite factorizar el codigo de transformacion en un metodo aparte
     */

    public static void main(String[] args) {
        log.info("Sin transform.");
        getCustomers()
                .doOnNext(customer -> log.info("Customer: {}", customer))
                .doOnComplete(()-> log.info("Customers completed"))
                .doOnError(error -> log.error("Error msg: {}", error.getMessage()))
                .subscribe();

        getPurchaseOrders()
                .doOnNext(po -> log.info("PurchaseOrder: {}", po))
                .doOnComplete(()-> log.info("PurchaseOrders completed"))
                .doOnError(error -> log.error("Error: {}", error.getMessage()))
                .subscribe();

        log.info("Con transform y Function.");
        var isDebuggerEnabled = true;

        getCustomers()
                .transform(isDebuggerEnabled ? addDebuggerWithFunction(): Function.identity())
                .subscribe();

        getPurchaseOrders()
                .transform(isDebuggerEnabled ? addDebuggerWithUnaryOp(): UnaryOperator.identity())
                .subscribe();
    }

    private static <T> Function<Flux<T>, Flux<T>> addDebuggerWithFunction() {
        return flux -> flux
                .doOnNext(item -> log.info("Item function: {}", item))
                .doOnComplete(()-> log.info("Completed Function"))
                .doOnError(error -> log.error("Error message function: {}", error.getMessage()));
    }

    private static <T> UnaryOperator <Flux<T>> addDebuggerWithUnaryOp() {
        return flux -> flux
                .doOnNext(item -> log.info("Item unary: {}", item))
                .doOnComplete(()-> log.info("Completed Unary"))
                .doOnError(error -> log.error("Error message Unary: {}", error.getMessage()));
    }

    private static Flux<Customer> getCustomers() {
        return Flux.range(1, 3)
                .map(item -> new Customer(item, Util.faker().name().fullName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5)
                .map(item -> new PurchaseOrder(
                        Util.faker().commerce().productName(),
                        item * 15,
                        Util.faker().random().nextInt(1, 5)
                ));
    }
}
