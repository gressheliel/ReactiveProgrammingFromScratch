package com.elhg.commons;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public class AbstractHttpClient {
    private static final String BASE_URL = "http://localhost:7070";
    protected final HttpClient httpClient;


    public AbstractHttpClient() {
        var loopResources = LoopResources.create("elhg", 1, true);
        this.httpClient = HttpClient.create()
                .runOn(loopResources)
                .baseUrl(BASE_URL);
    }
}
