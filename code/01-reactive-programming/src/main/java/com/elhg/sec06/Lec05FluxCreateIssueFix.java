package com.elhg.sec06;

import com.elhg.commons.Util;
import com.elhg.sec04.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec05FluxCreateIssueFix {

    public static void main(String[] args) {
        var generator = new NameGenerator();
        //var flux = Flux.create(generator); // No permite mas que un subscriber, el ultimo
        var flux = Flux.create(generator).share(); // Permite multiples subscribers

        flux.subscribe(Util.getDefaultSubscriber("Sam"));
        flux.subscribe(Util.getDefaultSubscriber("Mike"));


        for(int i = 0; i<10 ; i++){
            generator.generate();
        }
    }
}
