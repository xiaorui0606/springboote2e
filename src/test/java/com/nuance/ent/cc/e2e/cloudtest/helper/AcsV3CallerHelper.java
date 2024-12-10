package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.nuance.ent.cc.e2e.cloudtest.config.AcsCallerConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestAcsConfig;
import com.nuance.ent.cc.e2e.cloudtest.utility.RetryUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.*;
import static org.assertj.core.util.DateUtil.now;
import static org.assertj.core.util.DateUtil.parse;

import java.net.URI;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * The type Acs caller helper.
 */
@Profile(" cloudtest")
@Component
@Slf4j
public class AcsV3CallerHelper extends TestHelper{

    private final Duration timeOut = Duration.of(30, ChronoUnit.SECONDS);
    @Autowired
    private AcsCallerTestData acsCallerTestData;
    @Autowired
    private AcsCallerConfig acsCallerConfig;
    @Autowired
    private TestAcsConfig testAcsConfig;

    private Response response;

    static Date lastTimeStamp;

    /**
     * Place call string.
     *
     * @param destinationNumber the destination number
     * @param sourceNumber      the source number
     * @param callHangUpTimeout call hang up timeout in seconds
     * @param language          Language
     * @return the string
     * @throws Exception the exception
     */
    public String placeCallV3(final String destinationNumber,
                            final String sourceNumber,
                            final int callHangUpTimeout,
                            final String language,
                            final String access_token) throws Exception {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        String reqBody = acsCallerTestData.getPlaceCallPostRequestV3(destinationNumber, sourceNumber, callHangUpTimeout, connString, language);
        RequestSpecification req = new RequestSpecBuilder().addHeader("Authorization","Bearer "+ access_token).setContentType(ContentType.JSON).setBaseUri(acsCallerConfig.getACSBaseURLMapping()).build();
        response = given().spec(req).body(reqBody).post(acsCallerConfig.getPlaceCallMapping());
        String testSessionId = response.jsonPath().get("testSessionId").toString();
        log.info("Test session started with id :" + testSessionId);
        return testSessionId;
    }

    /**
     * Gets test session response.
     *
     * @param testSessionId the test session id
     * @return the test session response
     * @throws Exception the exception
     */
    public String getTestSessionResponse(final String testSessionId, final String access_token) throws Exception {
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTestSessionMapping(), testSessionId));
        String responseBody = response.asString();
        return responseBody;
    }

    /**
     * Gets total participants.
     *
     * @param testSessionId the test session id
     * @param access_token the access_token
     * @return the total participants
     * @throws Exception the exception
     */
    public int getTotalParticipants(final String testSessionId,final String access_token) throws Exception {
        var r = getTestSessionResponse(testSessionId,access_token);
        var totalParticipants = JsonPath.read(r, "$.callSessionList[0].data.totalParticipants");
        log.info("Total Participants found in the call " + totalParticipants.toString());
        return Integer.parseInt(totalParticipants.toString());
    }
    /**
     * Hangs up the call.
     *
     * @param testSessionId the test session id
     * @param access_token the access_token
     * @return the total participants
     * @throws Exception the exception
     */
    public String callHangUp(final String testSessionId, final String access_token) throws Exception {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var callHangUpIdType = "TEST_SESSION";
        String reqBody = acsCallerTestData.getCallHangUpPostRequest(testSessionId,connString,callHangUpIdType);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getCallHangUpMapping());
        var responseValue = response.jsonPath().get("response").toString();
        log.info("Call Hanged Up successfully: " + responseValue);
        return responseValue;
    }

    /**
     * Gets conversation ids from test session id.
     *
     * @param testSessionId the test session id
     * @return the conversation ids from test session id
     * @throws Exception the exception
     */
    public List<String> getConversationIdsFromTestSessionId(final String testSessionId, final String access_token) throws Exception {
            try {
                Response response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getConversationFromTestMapping(), testSessionId));
                String responseBody = response.asString();
                var responseListBody = new ObjectMapper().readValue(responseBody, List.class);
                return responseListBody;
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(1);
            }

        throw new TimeoutException("Unable to find callSession ids");
    }

    /**
     * Gets Caller Events response.
     *
     * @param testSessionId the test session id
     * @param access_token the access token
     * @return the caller events response
     * @throws Exception the exception
     */
    public Response getCallerEventsResponse(final String testSessionId, final String access_token) throws Exception {
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getACSV3CallerEventsMapping(), testSessionId));
        log.info("Event Message from Caller events" + response.asString() );
        return response;
    }

    /**
     * Sends DTMFTone from C2 to HM.
     *
     * @param testSessionId the test session id
     * @param access_token the access_token
     * @param dtmfTone the dtmfTone
     * @return the status code
     * @throws Exception the exception
     */
    public int sendDTMFUsingTestSessionId(final String testSessionId, final String access_token, final String dtmfTone) throws Exception {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var idType = "TEST_SESSION";
        String reqBody = acsCallerTestData.postSendDTMFRequest(testSessionId,dtmfTone,idType,connString);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getSendDTMFCallMapping());
        var responseValue = response.getStatusCode();
        log.info("dtmf tone sent successfully: " + dtmfTone);
        return responseValue;
    }

    /**
     * Gets Transcript of the call.
     *
     * @param testSessionID the test session id
     * @param access_token the access_token
     * @return the transcript value
     * @throws Exception the exception
     */
    public boolean getTranscriptResponse(final String testSessionID, final String access_token, final String expectedMessage) throws Exception {
        AcsV3CallerHelper callerHelper = new AcsV3CallerHelper();
        List<String> conversationIds = getConversationIdsFromTestSessionId(testSessionID,access_token);
        // Need to change this to loop and get ArrayList of conversation Ids
        String conversationId = conversationIds.get(0).toString();
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTranscriptMapping(), conversationId));
        String jsonData = response.asString();
        //log.info("response of getTranscript API : " + jsonData);
        if(lastTimeStamp!=null) {
            boolean msgFound = jsonValidator(jsonData,expectedMessage);
            return msgFound;
        }else{
            Response response1 = getCallerEventsResponse(testSessionID, access_token);
            JSONArray jsonArray = new JSONArray(response1.body().asString());
            lastTimeStamp = parseDate(jsonArray.getJSONObject(0).getString("eventTimeStamp"));
            boolean msgFound = jsonValidator(jsonData,expectedMessage);
            return msgFound;
        }

    }

    public static boolean jsonValidator(String jsonData, String expectedMessage) {
        List<String> transcript = com.jayway.jsonpath.JsonPath.read(jsonData, "$..transcriptValue");
        List<String> timestamp = com.jayway.jsonpath.JsonPath.read(jsonData, "$..timeStamp");
        TreeMap<Date, String> map = new TreeMap<>();
        Date d = now();

        for (int i = 0; i < transcript.size(); i++) {
            d = parseDate(timestamp.get(i));
            map.put(d, transcript.get(i));
        }
        List<String> listOfResponses = new ArrayList<String>(map.values());
        String str = expectedMessage;
        boolean msgFound = false;
        while (d.after(lastTimeStamp)) {
            if (listOfResponses.stream().anyMatch(s -> s.matches("(.*)" + str + "(.*)"))) {
                msgFound = true;
                log.info("response of getTranscript API when expected message found: " + jsonData);
            } else {
                msgFound = false;
                log.info("response of getTranscript API when expected message not found : " + jsonData);
            }
            break;
        }
        lastTimeStamp = map.lastEntry().getKey();
        listOfResponses.clear();
        map.clear();
        return msgFound;

    }

    public static Date parseDate(String date) {
        Date dat = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dat = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dat;
    }

    /**
     * Sends DTMFTone from C2 to HM.
     *
     * @param testSessionId the test session id
     * @param access_token the access_token
     * @param speechText the speechText
     * @return the status code
     * @throws Exception the exception
     */
    public String speakTTSUsingTestSessionId(final String testSessionId, final String access_token, final String speechText) throws Exception {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var idType = "TEST_SESSION";
        String reqBody = acsCallerTestData.postSpeakTTSRequest(testSessionId,idType,connString,speechText);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getSpeakTTSCallMapping());
        var responseValue = response.jsonPath().getString("status");
        log.info("speech text sent successfully: " + speechText);
        return responseValue;
    }

    /**
     * Gets Generic Events response.
     *
     * @param conversationId the conversation id
     * @param access_token the access token
     * @return the caller events response
     * @throws Exception the exception
     */
    public String getGenericCallerEventsResponse(final String conversationId, final String access_token) throws Exception {
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getGenericCallEventsMapping(), conversationId));
        JSONArray jsonArray = new JSONArray(response.body().asString());
        String responseBody = jsonArray.getJSONObject(1).getString("eventMessage");
        return responseBody;
    }

    /**
     * Gets test session response.
     *
     * @param testSessionId the test session id
     * @return the test session status
     * @throws Exception the exception
     */
    public String getTestSessionStatus(final String testSessionId, final String access_token) throws Exception {
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTestSessionStatusMapping(), testSessionId));
        String responseBody = response.asString();
        return responseBody;
    }

    /**
     * Gets test session summary.
     *
     * @param testSessionId the test session id
     * @return the test session summary
     * @throws Exception the exception
     */
    public String getTestSessionSummary(final String testSessionId, final String access_token) throws Exception {
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTestSessionStatusMapping(), testSessionId));
        String responseBody = response.asString();
        return responseBody;
    }

}
