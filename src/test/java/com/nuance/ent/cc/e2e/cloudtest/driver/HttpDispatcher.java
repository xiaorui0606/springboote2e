package com.nuance.ent.cc.e2e.cloudtest.driver;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;


/**
 * Handle boiler plate http request/response code.
 */
@Profile(" cloudtest")
@Component
public class HttpDispatcher {

    private final HttpClient webclient;

    /**
     * Instantiates a new Http dispatcher.
     *
     * @param webclient the webclient
     */
    public HttpDispatcher(final HttpClient webclient) {
        this.webclient = webclient;
    }

    /**
     * Dispatch.
     *
     * @param request        the request
     * @param expectedStatus the expected status
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public void dispatch(final HttpRequest request, final List<Integer> expectedStatus) throws IOException, InterruptedException {
        HttpResponse<String> response = webclient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        if (!expectedStatus.contains(status)) {
            throw new IllegalStateException(String.format(Locale.ROOT, "HttpResponse status [%s] not in expected status %s", status, expectedStatus));
        }
    }

    /**
     * Dispatch string.
     *
     * @param request the request
     * @return response body
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public String dispatch(final HttpRequest request) throws IOException, InterruptedException {
        return webclient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

}
