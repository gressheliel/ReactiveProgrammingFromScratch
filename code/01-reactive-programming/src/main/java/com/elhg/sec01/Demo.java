package com.elhg.sec01;


/*
    1. Publisher does not produce data unless subscriber requests for it.
    2. Publisher will produce only <= subscriber.requested() items. Publisher can also produce 0 item
    3. Subscriber can cancel the subscription. Producer should stop at that moment as subscriber is no longer
         interested in the data.
    4. Producer can send error signal to indicate  something is wrong.
   */

import java.time.Duration;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        demo3();
    }


    private static  void demo1(){
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
    }

    private static  void demo2() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

    }


    private static  void demo3() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

    }
}
