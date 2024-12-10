package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.nuance.ent.cc.e2e.cloudtest.driver.HttpDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;

/**
 * Handle creation of webhooks.
 */
//Check:OFF: MagicNumber
@Profile("cloudtest")
@Component
@Slf4j
public class WebHookHelper {

    /**
     * The Max retry.
     */
    final int maxRetry = 30;
    private final HttpDispatcher httpDispatcher;
    private final SubscriptionValidationTestData subscriptionValidationTestData;
    @Qualifier("armRequestBuilder")
    private final HttpRequest.Builder armRequestBuilder;


    /**
     * Instantiates a new Web hook helper.
     *
     * @param httpDispatcher                 the http dispatcher
     * @param subscriptionValidationTestData the subscription validation test data
     * @param armRequestBuilder              the arm request builder
     */
    public WebHookHelper(final HttpDispatcher httpDispatcher,
                         final SubscriptionValidationTestData subscriptionValidationTestData,
                         final HttpRequest.Builder armRequestBuilder) {
        this.httpDispatcher = httpDispatcher;
        this.subscriptionValidationTestData = subscriptionValidationTestData;
        this.armRequestBuilder = armRequestBuilder;
    }

    /**
     * Creates a webhook with a generic subject filter "caller" to match all IncomingCallEvents.
     *
     * @param routingId the routing id
     * @param randomId  the random id
     * @throws Exception the exception
     */
    public void createWebHook(final String routingId, final String randomId) throws Exception {
        httpDispatcher.dispatch(buildWebHookCreationRequest(routingId, null, randomId), List.of(201));
        assertWebHookCreationSucceeded(randomId);
    }

    /**
     * Create a webhook with a subject filter containing the values in {@code subjectContains}.
     *
     * @param routingId       the routing id
     * @param subjectEndsWith the subject ends with
     * @param randomId        the random id
     * @throws Exception the exception
     */
    public void createWebHookWithSubjectFiltering(final String routingId, final String subjectEndsWith, final String randomId) throws Exception {
        httpDispatcher.dispatch(buildWebHookCreationRequest(routingId, subjectEndsWith, randomId), List.of(201));
        assertWebHookCreationSucceeded(randomId);
    }

    /**
     * Build web hook creation request http request.
     *
     * @param routingId       the routing id
     * @param subjectEndsWith the subject ends with
     * @param randomId        the random id
     * @return the http request
     * @throws Exception the exception
     */
    HttpRequest buildWebHookCreationRequest(final String routingId, final String subjectEndsWith, final String randomId) throws Exception {
        var url = subscriptionValidationTestData.getSVUrl(randomId);
        final String requestBody = subscriptionValidationTestData.getSubscriptionValidationPayload(routingId, subjectEndsWith);
        var request = armRequestBuilder.uri(URI.create(url)).PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        log.info("WebHook Request Body: {}", requestBody);
        log.info("WebHook creation request: {}", request.uri());
        return request;
    }

    /**
     * Build web hook verification request http request.
     *
     * @param randomId the random id
     * @return the http request
     */
    HttpRequest buildWebHookVerificationRequest(final String randomId) {
        var url = subscriptionValidationTestData.getSVUrl(randomId);
        var request = armRequestBuilder.uri(URI.create(url)).GET().build();
        log.info("WebHook verification request: {}", request.uri());
        return request;
    }

    /**
     * Assert web hook creation succeeded.
     *
     * @param routingId the routing id
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    void assertWebHookCreationSucceeded(final String routingId) throws InterruptedException, IOException {
        var status = "Failed";
        var request = buildWebHookVerificationRequest(routingId);
        for (int i = 0; i < maxRetry; i++) {
            log.info("WebHook status check ({})...", i);

            var response = httpDispatcher.dispatch(request);
            status = subscriptionValidationTestData.getSVDeploymentStatus(response);
            if ("Creating".equals(status)) {
                sleep(1000);
            } else {
                if (!"Succeeded".equals(status)) {
                    log.error(response);
                }
                break;
            }
        }
        log.info("WebHook creation status: {}", status);
        if (!"Succeeded".equals(status)) {
            throw new IllegalStateException(String.format(Locale.ROOT, "WebHook creating ended with status: %s", status));
        }
    }


    /**
     * Delete web hook.
     *
     * @param routingId the routing id
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public void deleteWebHook(final String routingId) throws IOException, InterruptedException {
        var request = armRequestBuilder.uri(URI.create(subscriptionValidationTestData.getSVUrl(routingId)))
                .DELETE()
                .build();
        log.info("WebHook deletion request: {}", request.uri());
        httpDispatcher.dispatch(request, List.of(202, 204));
    }
}
