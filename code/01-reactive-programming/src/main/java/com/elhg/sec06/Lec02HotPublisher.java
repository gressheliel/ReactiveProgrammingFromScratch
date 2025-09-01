package com.elhg.sec06;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec02HotPublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec02HotPublisher.class);

    public static void main(String[] args) throws InterruptedException {
        //var movieFlux = movieStream();   // Cold Publisher, Sam and Mike will see the movie from the beginning
        var movieFlux = movieStream().share(); // Hot Publisher, Sam and Mike will see the movie from the moment they join

        Util.sleepSeconds(2);
        movieFlux.subscribe(Util.getDefaultSubscriber("Sam"));

        Util.sleepSeconds(3);
        movieFlux
                .take(3)   // Mike will see only 3 scenes
                .subscribe(Util.getDefaultSubscriber("Mike"));

        Util.sleepSeconds(15);
    }


    //Movie Theater
    private static Flux<String> movieStream(){
        return Flux.generate(
                ()->{
                    log.info("Movie is starting...");
                    return 1;
                },
                (state, sink) -> {
                    String scene = "Movie Scene: " + state;
                    log.info("Emitting: {}", scene);
                    sink.next(scene);
                    return ++state;
                }
        ).take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
