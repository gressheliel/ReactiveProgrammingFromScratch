package com.elhg.sec05.assignment;

import com.elhg.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec11Assignment {
    public static final Logger log = LoggerFactory.getLogger(Lec11Assignment.class);

    /**
     * Assignment:
     * 1. Use the ExternalServiceClient to call getProductName for product ids from 1 to 4.
     * 2. Get the product names using the product-service
     * 3. If the call takes more than 2 seconds, fallback to the timeout-fallback endpoint
     * 4. If the product-service returns an empty response, fallback to the empty-fallback endpoint
     * 5. Subscribe and log the product names
     *
     */

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        Flux.range(1,4)
                .flatMap(client::getProductName)
                .subscribe(Util.getDefaultSubscriber());

        /*for(int i=1; i<=4; i++){
            client.getProductName(i)
                    .subscribe(Util.getDefaultSubscriber());
        }*/

        Util.sleepSeconds(3);
    }

}
