package com.elhg.sec07;

import com.elhg.commons.Util;

public class Lec06EventLoopIssueFix {
    /**
     * Se agrega publishOn en el ExternalServiceClient para evitar que el event loop del servidor web
      *
     */
    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for(int i=1;i<=5; i++){
            client.getProductName(i)
                    .map(Lec06EventLoopIssueFix::process)
                    .subscribe(Util.getDefaultSubscriber());
        }

        Util.sleepSeconds(10);
    }

    private static String process(String input){
        Util.sleepSeconds(1);
        return input + "-processed";
    }
}
