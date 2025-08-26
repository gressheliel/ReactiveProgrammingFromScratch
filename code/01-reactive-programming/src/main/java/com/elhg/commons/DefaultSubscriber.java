package com.elhg.commons;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubscriber<T> implements Subscriber<T> {

    private static final Logger log = LoggerFactory.getLogger(DefaultSubscriber.class);
    private final String name;

    public DefaultSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("{}, Subscribed successfully.", this.name);
        subscription.request(Long.MAX_VALUE); // Request an unbounded number of items
    }

    @Override
    public void onNext(T t) {
        log.info("{} , Received : {}", this.name, t);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("{} , Error occurred: {}", this.name,throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("{}, Subscription completed successfully.", this.name);
    }
}

