package com.elhg.sec04.helper;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {
   private FluxSink<String> sink;
   private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        log.info("Inside accept method");
        this.sink = stringFluxSink;
    }

    public void generate(){
        this.sink.next(Util.faker().name().fullName());
    }


}
