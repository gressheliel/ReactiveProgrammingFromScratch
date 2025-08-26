package com.elhg.sec03.assigment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceSubscriber implements Subscriber<Integer> {
    private static final Logger log = LoggerFactory.getLogger(StockPriceSubscriber.class);
    private int balance = 0;
    private int quantity = 0;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        this.balance = 1000; // Initial balance
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer stock) {
        if(stock < 90 && balance >= stock) {
            quantity++;
            balance -= stock;
            log.info("Bought stock {}. New balance: {}", quantity, balance);
        } else if(stock > 110 && quantity > 0) {
            log.info("Sold stocks {} at price: {}", quantity, stock);
            balance += stock * quantity;
            quantity = 0;
            log.info("New balance after selling: {}", balance);
            subscription.cancel();
        } else {
            log.info("No action taken for stock price: {}. Balance remains: {}", stock, balance);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("Stock price stream completed.");
    }

}
