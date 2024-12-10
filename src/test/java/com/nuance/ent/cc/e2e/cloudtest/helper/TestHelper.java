package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.nuance.ent.cc.e2e.cloudtest.config.OcSimConfig;
import com.nuance.ent.cc.e2e.cloudtest.driver.AzureKeyVaultDriver;
import com.nuance.ent.cc.e2e.cloudtest.driver.HttpDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

/**
 * The type Test helper.
 */
@Component
public class TestHelper {

    /**
     * Sleep.
     *
     * @param timeInSeconds the time in seconds
     */
    @Autowired
    protected OcSimConfig ocSimConfig;
    /**
     * The Http dispatcher.
     */
    @Autowired
    protected HttpDispatcher httpDispatcher;
    /**
     * The Cc request builder.
     */
    @Autowired
    @Qualifier("ccRequestBuilder")
    protected HttpRequest.Builder ccRequestBuilder;
    /**
     * The Key vault driver.
     */
    @Autowired
    protected AzureKeyVaultDriver keyVaultDriver;
    /**
     * The Azure key vault driver.
     */
    @Autowired
    protected AzureKeyVaultDriver azureKeyVaultDriver;

    /**
     * Sleep.
     *
     * @param timeInSeconds the time in seconds
     */
    public static void sleep(final int timeInSeconds) {
        final var finalTime = timeInSeconds * 1000;
        try {
            Thread.sleep(finalTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
