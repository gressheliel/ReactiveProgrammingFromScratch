package com.elhg.sec04.assigment;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class MainFileReader {
    private static final Logger log = LoggerFactory.getLogger(MainFileReader.class);

    public static void main(String[] args) {
        var fileReaderService = new FileReaderServiceImpl();

        var path = Path.of("/home/gresshel/workspace/projects/ReactiveProgrammingFromScratch/code/01-reactive-programming/src/main/resources/sec04/file.txt");

        fileReaderService.read(path)
                .takeUntil(line ->line.equalsIgnoreCase("line10"))
                .subscribe(Util.getDefaultSubscriber());
    }
}
