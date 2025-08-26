package com.elhg.sec01;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);
    private Subscriber<? super String> subscriber;
    private boolean isCancelled;
    private final Faker faker;
    private static final int MAX_ITEMS = 10;
    private int count = 0;


    public SubscriptionImpl (Subscriber<? super String> subscriber){
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if(isCancelled){
            log.warn("Subscription is cancelled, cannot request more items.");
            return;
        }
        log.info("Requesting {} items from the publisher.", requested);
        for (long i = 0; i < requested && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(faker.internet().emailAddress());
        }
        if(count == MAX_ITEMS) {
            log.info("Max items reached, completing subscription.");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {

    }
}
