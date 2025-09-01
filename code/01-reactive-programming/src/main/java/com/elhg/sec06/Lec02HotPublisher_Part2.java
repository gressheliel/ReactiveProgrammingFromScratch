package com.elhg.sec06;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec02HotPublisher_Part2 {
    private static final Logger log = LoggerFactory.getLogger(Lec02HotPublisher_Part2.class);

    public static void main(String[] args) throws InterruptedException {
        //var movieFlux = movieStream();   // Cold Publisher, Sam and Mike will see the movie from the beginning
        //var movieFlux = movieStream().share(); // Hot Publisher, Sam and Mike will see the movie from the moment they join
        var movieFlux = movieStream()
                        .publish()
                        .refCount(3); // Hot Publisher, Sam and Mike will see the movie if exists a minimum of 'N' subscriber
                                            // 0 -> Error, 1 -> Ok, 2-> 0k, 3 -> Never start the movie
        Util.sleepSeconds(3);
        movieFlux
                .take(2) //Sam will see only 2 scenes
                .subscribe(Util.getDefaultSubscriber("Sam"));

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
