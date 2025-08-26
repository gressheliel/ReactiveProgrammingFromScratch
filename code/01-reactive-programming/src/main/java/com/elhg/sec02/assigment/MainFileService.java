package com.elhg.sec02.assigment;

import com.elhg.commons.Util;

public class MainFileService {
    public static void main(String[] args) {
        FileService fileService = new FileServiceImpl();
        String fileName = "test.txt";
        String content = "Hello, Reactive World!";

        // Write to file
        fileService.write(fileName, content)
                .subscribe(Util.getDefaultSubscriber("Write"));


        // Read from file
        fileService.read(fileName)
                .subscribe(Util.getDefaultSubscriber("Read"));

        // Delete file
        fileService.delete(fileName)
                .subscribe(Util.getDefaultSubscriber("Delete"));
    }
}
