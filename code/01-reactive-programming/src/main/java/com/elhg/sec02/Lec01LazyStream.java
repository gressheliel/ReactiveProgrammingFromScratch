package com.elhg.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Lec01LazyStream {

    private static final Logger log = LoggerFactory.getLogger(Lec01LazyStream.class);

    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5)
                .peek(i -> log.info("No imprime nada, requiere un operador terminal: {}", i));

        var lista = Stream.of(1, 2, 3)
                .peek(i -> log.info("Valor: {}", i))
                .toList();

    }

}
