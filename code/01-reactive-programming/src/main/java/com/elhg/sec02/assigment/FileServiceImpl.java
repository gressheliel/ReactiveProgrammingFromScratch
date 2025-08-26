package com.elhg.sec02.assigment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class FileServiceImpl implements FileService{

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(()->{
            try {
                Path path = Path.of(fileName);
                if(!Files.exists(path)){
                    Files.writeString(path, content, StandardOpenOption.CREATE);
                }
                Files.writeString(path, content, StandardOpenOption.APPEND);
            } catch (Exception e) {
                log.error("Exception occurred while writing to file: {}" , e.getMessage());
            }
        });
    }

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromCallable(() -> Files.readString(Path.of(fileName)));
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(()->{
            try {
                Files.deleteIfExists(Path.of(fileName));
            } catch (Exception e) {
                log.error("Exception occurred while deleting file: {}" , e.getMessage());
            }
        });
    }
}
