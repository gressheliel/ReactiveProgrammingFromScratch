package com.elhg.sec05;

import com.elhg.commons.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {

    // handle is a combination of filter and map
    // Requerimiento:
    /*
    Emite 1 => Devuelve -2
    Emite 4 => No se envia
    Emite 7 => Error
    Emite Cualquier otro => Se envía
     */
    // 1. Emitir solo numeros pares


    public static void main(String[] args) {
        Flux<Integer> fluxInteger = Flux.range(1, 10);

        fluxInteger.handle(
                        (item, sink) -> {
                            switch (item) {
                                case 1 -> sink.next(-2);
                                case 4 -> {
                                    // No se envia nada
                                }
                                case 7 -> sink.error(new RuntimeException("7 no es permitido"));
                                default -> sink.next(item);
                            }
                        }
                ).cast(Integer.class)
                .subscribe(Util.getDefaultSubscriber());

    }
}
