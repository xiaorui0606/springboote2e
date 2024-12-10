package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.nuance.ent.cc.e2e.cloudtest.config.AcsCallerConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestAcsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

/**
 * The type Acs caller helper.
 */
@Profile(" cloudtest")
@Component
@Slf4j
public class AcsCallerHelper extends TestHelper {

    private final Duration timeOut = Duration.of(30, ChronoUnit.SECONDS);
    @Autowired
    private AcsCallerTestData acsCallerTestData;
    @Autowired
    private AcsCallerConfig acsCallerConfig;
    @Autowired
    private TestAcsConfig testAcsConfig;

    /**
     * Place call string.
     *
     * @param destinationNumber the destination number
     * @param sourceId          the source id
     * @param audioFileName     the audio file name
     * @param personShortName   the person short name
     * @param audioLoopTimeOut  the audio loop time out
     * @return the string
     * @throws Exception the exception
     */
    public String placeCall(final String destinationNumber,
                            final String sourceId,
                            final String audioFileName,
                            final String personShortName, final int audioLoopTimeOut) throws Exception {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        HttpRequest placeCallRequest = ccRequestBuilder
                .uri(URI.create(acsCallerConfig.getPlaceCallMapping()))
                .POST(HttpRequest.BodyPublishers.ofString(acsCallerTestData.getPlaceCallPostRequest(destinationNumber, sourceId, audioLoopTimeOut, audioFileName, connString, personShortName)))
                .build();
        log.info("Creating placeCall: {}", placeCallRequest.uri());
        String responseBody = httpDispatcher.dispatch(placeCallRequest);
        var testSessionId = getJsonValueByPath(responseBody, "$.testSessionId").toString();
        log.info("Created test session: {}", testSessionId);
        return testSessionId;
    }

    /**
     * Gets test session response.
     *
     * @param testSessionId the test session id
     * @return the test session response
     * @throws Exception the exception
     */
    public String getTestSessionResponse(final String testSessionId) throws Exception {
        HttpRequest testSessionRequest = ccRequestBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTestSessionMapping(), testSessionId)))
                .GET()
                .build();
        log.info("Getting test session with URI {}", testSessionRequest.uri());
        String responseBody = httpDispatcher.dispatch(testSessionRequest);
        return responseBody;
    }

    /**
     * Gets total participants.
     *
     * @param testSessionId the test session id
     * @return the total participants
     * @throws Exception the exception
     */
    public int getTotalParticipants(final String testSessionId) throws Exception {
        var r = getTestSessionResponse(testSessionId);
        var totalParticipants = JsonPath.read(r, "$.callSessionList[0].data.totalParticipants");
        return Integer.parseInt(totalParticipants.toString());
    }

    /**
     * Is call connected boolean.
     *
     * @param callSessionId the call session id
     * @return the boolean
     */
    public boolean isCallConnected(final String callSessionId) {
        try {
            return waitForEvent(callSessionId, EventTypes.CALL_CONNECTED_EVENT);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Is participants updated boolean.
     *
     * @param callSessionId the call session id
     * @return the boolean
     */
    public boolean isParticipantsUpdated(final String callSessionId) {
        try {
            return waitForEvent(callSessionId, EventTypes.PARTICIPANT_UPDATED);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets json value by path.
     *
     * @param response the response
     * @param jsonPath the json path
     * @return the json value by path
     */
    public Object getJsonValueByPath(final String response, final String jsonPath) {
        try {
            var result = JsonPath.read(response, jsonPath);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Wait for call connected.
     *
     * @param testSessionId the test session id
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean waitForCallConnected(final String testSessionId) throws Exception {
        var callSessionIdList = getConversationIdsFromTestSessionId(testSessionId);
        LinkedList<Boolean> callHangUpStatusList = new LinkedList<>();
        callSessionIdList.stream().forEach(id -> {
            callHangUpStatusList.add(isCallConnected(id));
        });
        return callHangUpStatusList.stream().allMatch(c -> c.equals(true));
    }

    /**
     * Check generic caller events list.
     *
     * @param conversationId the conversation id
     * @return the list
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public List<HashMap<String, String>> checkGenericCallerEvents(final String conversationId) throws IOException, InterruptedException {
        HttpRequest testSessionRequest = ccRequestBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getGenericCallEventsMapping(), conversationId)))
                .GET()
                .build();
        log.info("Getting test session with URI {}", testSessionRequest.uri());
        String responseBody = httpDispatcher.dispatch(testSessionRequest);
        var responseJsonBody = new ObjectMapper().readValue(responseBody, List.class);
        return responseJsonBody;
    }

    /**
     * Check acs caller events list.
     *
     * @param conversationId the conversation id
     * @return the list
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public List<String> checkAcsCallerEvents(final String conversationId) throws IOException, InterruptedException {
        HttpRequest testSessionRequest = ccRequestBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getAcsCallEventsMapping(), conversationId)))
                .GET()
                .build();
        log.info("Getting test session with URI {}", testSessionRequest.uri());
        String responseBody = httpDispatcher.dispatch(testSessionRequest);
        var responseJsonBody = new ObjectMapper().readValue(responseBody, List.class);
        return responseJsonBody;
    }

    /**
     * Gets conversation ids from test session id.
     *
     * @param testSessionId the test session id
     * @return the conversation ids from test session id
     * @throws Exception the exception
     */
    public List<String> getConversationIdsFromTestSessionId(final String testSessionId) throws Exception {
        HttpRequest testSessionRequest = ccRequestBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getConversationFromTestMapping(), testSessionId)))
                .GET()
                .build();
        log.info("Getting test session with URI {}", testSessionRequest.uri());
        int timeOutInSeconds = ocSimConfig.getCallCompletionTimeoutSeconds() + 1;
        Instant deadline = Instant.now().plus(Duration.of(timeOutInSeconds, ChronoUnit.SECONDS));
        while (Instant.now().isBefore(deadline)) {
            try {
                String responseBody = httpDispatcher.dispatch(testSessionRequest);
                var responseListBody = new ObjectMapper().readValue(responseBody, List.class);
                return responseListBody;
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(1);
            }
        }
        throw new TimeoutException("Unable to find callSession ids");
    }

    /**
     * Gets call session response.
     *
     * @param testSessionId the test session id
     * @return the call session response
     * @throws Exception the exception
     */
    public String getCallSessionResponse(final String testSessionId) throws Exception {
        HttpRequest testSessionRequest = ccRequestBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTestSessionMapping(), testSessionId)))
                .GET()
                .build();
        log.info("Getting test session with URI {}", testSessionRequest.uri());
        int timeOutInSeconds = ocSimConfig.getCallCompletionTimeoutSeconds() + 1;
        Instant deadline = Instant.now().plus(Duration.of(timeOutInSeconds, ChronoUnit.SECONDS));
        while (Instant.now().isBefore(deadline)) {
            try {
                String responseBody = httpDispatcher.dispatch(testSessionRequest);
                return responseBody;
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(1);
            }
        }
        throw new TimeoutException("Unable to find callSession ids");
    }

    /**
     * Wait for call session hangup boolean.
     *
     * @param callSessionId the call session id
     * @return the boolean
     */
    public boolean waitForCallSessionHangup(final String callSessionId) {
        int timeOutInSeconds = ocSimConfig.getCallCompletionTimeoutSeconds() + 6;
        Instant deadline = Instant.now().plus(Duration.of(timeOutInSeconds, ChronoUnit.SECONDS));
        while (Instant.now().isBefore(deadline)) {
            try {
                var r = checkGenericCallerEvents(callSessionId);
                if (r.stream().filter(c -> c.get("eventMessage").equalsIgnoreCase(EventTypes.CALL_HANGUP)).count() == 1) {
                    log.info(EventTypes.CALL_HANGUP);
                    return true;
                } else {
                    sleep(5);
                }
            } catch (Exception e) {
                sleep(5);
            }
        }
        return false;
    }

    /**
     * Wait for test session hang up boolean.
     *
     * @param testSessionId the test session id
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean waitForTestSessionHangUp(final String testSessionId) throws Exception {
        var callSessionId = getConversationIdsFromTestSessionId(testSessionId);
        LinkedList<Boolean> callHangUpStatusList = new LinkedList<>();
        callSessionId.stream().parallel().forEach(id -> {
            callHangUpStatusList.add(waitForCallSessionHangup(id));
        });
        return callHangUpStatusList.stream().allMatch(c -> c.equals(true));
    }

    /**
     * Wait for event boolean.
     *
     * @param callSessionId the call session id
     * @param eventType     the event type
     * @return the boolean
     */
    public boolean waitForEvent(final String callSessionId, String eventType) {
        int timeOutInSeconds = ocSimConfig.getCallCompletionTimeoutSeconds() + 1;
        Instant deadline = Instant.now().plus(Duration.of(timeOutInSeconds, ChronoUnit.SECONDS));
        while (Instant.now().isBefore(deadline)) {
            try {
                var r = checkAcsCallerEvents(callSessionId);
                if (r.toString().contains(eventType)) {
                    log.info(eventType);
                    return true;
                } else {
                    sleep(1);
                }
            } catch (Exception e) {
                sleep(1);
            }
        }
        return false;
    }

    public String callHangUp(final String testSessionId) throws Exception {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var callHangUpIdType = "TEST_SESSION";
        HttpRequest callHangUpRequest = ccRequestBuilder
                .uri(URI.create(acsCallerConfig.getCallHangUpMapping()))
                .POST(HttpRequest.BodyPublishers.ofString(acsCallerTestData.getCallHangUpPostRequest(testSessionId,connString,callHangUpIdType)))
                .build();
        log.info("Creating CallHangUp: {}", callHangUpRequest.uri());
        String responseBody = httpDispatcher.dispatch(callHangUpRequest);
        //log.info("responseBody of callHangUp request: "+ responseBody);
        var response = getJsonValueByPath(responseBody, "$.response").toString();
        log.info("Call Hanged Up successfully: ", response);
        return response;
    }

}
