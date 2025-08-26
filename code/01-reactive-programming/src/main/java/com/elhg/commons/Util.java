package com.elhg.commons;

import com.github.javafaker.Faker;

public class Util {

    private static final Faker faker = Faker.instance();

    public static <T> DefaultSubscriber<T> getDefaultSubscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static <T> DefaultSubscriber<T> getDefaultSubscriber() {
        return new DefaultSubscriber<>("");
    }

    public static Faker faker(){
        return faker;
    }

    public static void sleepSeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
