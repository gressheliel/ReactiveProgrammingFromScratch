package com.elhg.sec03;

import com.elhg.commons.Util;
import com.elhg.sec03.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08NonBlockingStreamingMessages {

    private static Logger log = LoggerFactory.getLogger(Lec08NonBlockingStreamingMessages.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        client.getNames()
                .subscribe(Util.getDefaultSubscriber());

        Util.sleepSeconds(6);

    }
}
