package com.elhg.sec02.assigment;

import reactor.core.publisher.Mono;

public interface FileService {
    Mono<Void> write(String fileName, String content);
    Mono<String> read(String fileName);
    Mono<Void> delete(String fileName);
}
