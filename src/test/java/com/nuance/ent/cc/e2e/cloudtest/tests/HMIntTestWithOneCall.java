package com.nuance.ent.cc.e2e.cloudtest.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuance.ent.cc.e2e.cloudtest.BaseCloudTest;
import com.nuance.ent.cc.e2e.cloudtest.config.AcsCallerConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestAcsConfig;
import com.nuance.ent.cc.e2e.cloudtest.driver.AzureKeyVaultDriver;
import com.nuance.ent.cc.e2e.cloudtest.helper.AcsCallerTestData;
import com.nuance.ent.cc.e2e.cloudtest.models.TestSessionStatus;
import com.nuance.ent.cc.e2e.cloudtest.models.TranscriptResponse;
import com.nuance.ent.cc.e2e.cloudtest.utility.RetryUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.TestNGException;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.util.DateUtil.now;
import static org.assertj.core.util.DateUtil.parse;

@Profile("cloudtest")
@Component
@Slf4j
public class HMIntTestWithOneCall extends BaseCloudTest  {

    @Autowired
    private AcsCallerTestData acsCallerTestData;
    @Autowired
    private AcsCallerConfig acsCallerConfig;
    @Autowired
    private TestAcsConfig testAcsConfig;
    @Autowired
    protected AzureKeyVaultDriver azureKeyVaultDriver;
    @Autowired
    protected AzureKeyVaultDriver keyVaultDriver;
    /**
     * Variables defined here
     */
    private Response response;
    private static String access_token;
    private static String testSessionId;
    private static String lastTimeStamp;
    public static RetryUtil<Boolean> retryUtil = new RetryUtil<>();;
    public static boolean msgFound;
    public static List<String> listOfConversationIds;
    public static Properties config = new Properties();
    public static FileInputStream fis;
    private String sentenceSeparator = "/n";

    public String getSentenceSeparator(){
        return sentenceSeparator;
    }

    @BeforeSuite
    public void setUp(){
        Date d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        lastTimeStamp = dateFormat.format(d);
        log.info("lastTimeStamp is initialized with: "+ lastTimeStamp);
        try {
            fis = new FileInputStream("./src/test/resources/cloudtest/testdata/properties/messages.properties");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            config.load(fis);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @AfterSuite
    public void tearDown(ITestContext context) throws IOException {
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var callHangUpIdType = "TEST_SESSION";
        String reqBody = acsCallerTestData.getCallHangUpPostRequest(testSessionId,connString,callHangUpIdType);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getCallHangUpMapping());
        var responseValue = response.jsonPath().get("response").toString();
        log.info("Call Hanged Up successfully from Tear Down: " + responseValue);
        log.info("Setting all variables to null values before next tests start");
        testSessionId=null;
        access_token=null;
        lastTimeStamp=null;
        log.info("tearDown successful");
    }


    @Test()
    public void getAccessToken(ITestContext context) {

        String reqBody = String.format(Locale.ROOT, "client_id=%s&client_secret=%s&grant_type=%s&scope=%s",
                azureKeyVaultDriver.getValueFromVault(spConfig.getClientIdKey()),
                azureKeyVaultDriver.getValueFromVault(spConfig.getClientSecretKey()),
                testEnvConfig.getCcOauthGrantType(), testEnvConfig.getCcOauthScope());

        String authTokenUrl = testEnvConfig.getCcOauthTokenUrl();
        Response response = given().contentType("application/x-www-form-urlencoded").body(reqBody).get(authTokenUrl);
        access_token = response.jsonPath().get("access_token").toString();
        context.setAttribute("access_token", access_token);
        sleep(4);
    }

    @Test()
    @Parameters({"targetNumber","sourceNumber", "callHangUpTimeout", "language"})
    public void placeCallXML(final String destinationNumber,
                            final String sourceNumber,
                            final int callHangUpTimeout,
                            final String language,
                            ITestContext context) throws Exception {
        access_token = (String) context.getAttribute("access_token");
        String targetNumber = config.getProperty(destinationNumber);
        System.out.println("TargetNumber for this call is : " + targetNumber);
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        String reqBody = acsCallerTestData.getPlaceCallPostRequestV3(targetNumber, sourceNumber, callHangUpTimeout, connString, language);
        RequestSpecification req = new RequestSpecBuilder().addHeader("Authorization","Bearer "+ access_token).setContentType(ContentType.JSON).setBaseUri(acsCallerConfig.getACSBaseURLMapping()).build();
        response = given().spec(req).body(reqBody).post(acsCallerConfig.getPlaceCallMapping());
        String testSessionId = response.jsonPath().get("testSessionId").toString();
        log.info("Test session started with id :" + testSessionId);
        context.setAttribute("testSessionId", testSessionId);
    }


    @Test()
    public void getCallerEvents(ITestContext context) throws JSONException {
        testSessionId = (String) context.getAttribute("testSessionId");
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getACSV3CallerEventsMapping(), testSessionId));
        log.info("Event Message from Caller events" + response.asString() );
        JSONArray jsonArray = new JSONArray(response.body().asString());
        String responseBody = jsonArray.getJSONObject(0).getString("eventMessage");
        lastTimeStamp = jsonArray.getJSONObject(0).getString("eventTimeStamp");
        log.info("getCallerEvents:lastTimeStamp: "+lastTimeStamp);
        Assert.assertEquals(responseBody, "Test session started");
    }

    @Test()
    public void getConversationId(ITestContext context) throws Exception {
        testSessionId = (String) context.getAttribute("testSessionId");
        var callSessionIdList = getConversationIdsFromTestSessionId(testSessionId, access_token);
        String conversationId = callSessionIdList.get(0);
        System.out.println(conversationId);
        Assert.assertTrue(!callSessionIdList.isEmpty());
        context.setAttribute("conversationId", conversationId);
    }

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

    public boolean getTranscriptResponseXML(final String expectedMessage, final Double matchingPercentage, ITestContext context) throws Exception {
        log.info("### expectedMessage: "+expectedMessage);
        List<TranscriptResponse> transcripts;
        Gson gson = new Gson();
        String conversationId = listOfConversationIds.get(0);
        sleep(2);
        response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTranscriptMapping(), conversationId));
        String jsonData = response.asString();
        transcripts = gson.fromJson(jsonData, new TypeToken<List<TranscriptResponse>>(){}.getType());
        log.info("Last Time Stamp value for next steps is : "+lastTimeStamp);
        boolean msgFound = false;
        for (int i=0;i < transcripts.size();i++){
            if(parseDate(transcripts.get(i).getTimeStamp()).after(parseDate(lastTimeStamp))){
                log.info("New Transcript fetched after previous timestamp: ["+ lastTimeStamp +"]: " + transcripts);
                List<String> filteredTranscriptList = transcripts.stream().filter(r->r.getTimeStamp().compareTo(lastTimeStamp)>0).map(TranscriptResponse::getTranscriptValue).collect(Collectors.toList());
                log.info("Filtered Transcript list contains transcripts with new timestamps: "+ filteredTranscriptList);
                msgFound = transcriptResponseWithTime(filteredTranscriptList,expectedMessage,matchingPercentage);
                break;
            }
        }
        if(msgFound) {
            lastTimeStamp = transcripts.get(transcripts.size() - 1).getTimeStamp();
        }
        return msgFound;

    }

    public boolean transcriptResponseWithTime(List<String> transcripts, final String expectedMessage, final Double matchingPercentage){
        if(expectedMessage.contains(getSentenceSeparator())) {
            var responseList = Arrays.stream(expectedMessage.split(getSentenceSeparator())).toList();
            List<String> matchedTranscripts = transcripts.stream().filter(entry -> responseList.stream()
                    .anyMatch(r -> new JaroWinklerSimilarity().apply(r, entry) >= matchingPercentage)).collect(Collectors.toList());
            int matchedCount = matchedTranscripts.size();
            log.info("Matched Transcript Count: " + matchedCount + ": "+ matchedTranscripts + "\n");
            return transcripts != null && matchedCount == responseList.size();
//            return transcripts != null && transcripts.stream().filter(entry -> responseList.stream()
//                    .anyMatch(r -> new JaroWinklerSimilarity().apply(r, entry.getTranscriptValue()) >= matchingPercentage)).count() == responseList.size();
//            return transcripts != null && transcripts.stream().filter(entry -> responseList.stream()
//                    .anyMatch(r -> new JaroWinklerSimilarity().apply(r, entry) >= matchingPercentage)).count() == responseList.size();

        }
        //log.info("Transcripts response contains : " + transcripts);
        return transcripts != null && transcripts.stream()
                .anyMatch(entry -> new JaroWinklerSimilarity().apply(expectedMessage, entry) >= matchingPercentage);
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

            if (listOfResponses.stream().anyMatch(s -> s.matches("(.*)" + str + "(.*)"))) {
                msgFound = true;
                log.info("response of getTranscript API when expected message found: " + jsonData);
            } else {
                msgFound = false;
                log.info("response of getTranscript API when expected message not found : " + jsonData);
            }

        //lastTimeStamp = map.lastEntry().getKey();
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

    public static void sleep(final int timeInSeconds) {
        final var finalTime = timeInSeconds * 1000;
        try {
            Thread.sleep(finalTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test()
    @Parameters("dtmfTone")
    public void sendDTMFUsingTestSessionId(final String dtmfTone) throws Exception {
//        sleep(5);
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var idType = "TEST_SESSION";
        String reqBody = acsCallerTestData.postSendDTMFRequest(testSessionId,dtmfTone,idType,connString);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getSendDTMFCallMapping());
        var responseValue = response.getStatusCode();
        log.info("dtmf tone sent successfully: " + dtmfTone);
        Assert.assertEquals(responseValue,202);
    }

    @Parameters("speechText")
    @Test()
    public void speakTTSUsingTestSessionId(final String speechText) throws Exception {
        String textToSend = config.getProperty(speechText);
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var idType = "TEST_SESSION";
        String reqBody = acsCallerTestData.postSpeakTTSRequest(testSessionId,idType,connString,textToSend);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getSpeakTTSCallMapping());
        var responseValue = response.jsonPath().getString("status");
        log.info("speech text sent successfully: " + speechText);
        Assert.assertEquals(responseValue,"success");
    }


    @Test()
    public void callHangUpXML(ITestContext context) throws IOException {
        if(testSessionId.isEmpty()){
            testSessionId = (String) context.getAttribute("testSessionId");
        }
        var connString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        var callHangUpIdType = "TEST_SESSION";
        String reqBody = acsCallerTestData.getCallHangUpPostRequest(testSessionId,connString,callHangUpIdType);
        response = given().auth().oauth2(access_token).contentType("application/json").body(reqBody).post(acsCallerConfig.getCallHangUpMapping());
        var responseValue = response.jsonPath().get("response").toString();
        log.info("Call Hanged Up successfully: " + responseValue);
    }

    @Parameters({"expectedMessage","matchingPercentage","maxDuration","delayInMilli"})
    @Test
    public void getTranscriptResponseUsingPolling(final String Message,Double matchingPercentage, int maxDuration, long delayInMilli, ITestContext context){
        String expectedMessage = config.getProperty(Message);
        msgFound = retryUtil.pollDuration(maxDuration,delayInMilli).method(()->getTranscriptResponseXML(expectedMessage,matchingPercentage, context)).setRetryPolicy(res -> res == Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
    }

    @Parameters("waitTime")
    @Test
    public void waitingForPreviousProcessToComplete(final int timeInSeconds){
        sleep(timeInSeconds);

    }
    @Test
    public void getTestSessionStatus(ITestContext context) throws Exception {
    testSessionId = (String) context.getAttribute("testSessionId");
    boolean callConnected = retryUtil.pollDuration(15,3000l).method(()->callConnected(testSessionId, access_token, context)).setRetryPolicy(r->r==Boolean.TRUE).execute();
    Assert.assertTrue(callConnected);
    }

    private boolean callConnected(final String testSessionId, final String access_token, ITestContext context) {
        boolean connected = false;
        Gson gson = new Gson();
        try {
            response = given().auth().oauth2(access_token).get(String.format(Locale.ROOT, "%s/%s", acsCallerConfig.getTestSessionStatusMapping(), testSessionId));
            String responseBody = response.asString();
            List<TestSessionStatus> list = gson.fromJson(responseBody, new TypeToken<List<TestSessionStatus>>(){}.getType());
            if(list.size() > 0) {
                var connectedList = list.stream().filter(status -> "connected".equals(status.getStatus())).toList();
                connected = connectedList.size() == list.size();
                if(connected) {
                    listOfConversationIds=list.stream().map(TestSessionStatus::getConversationId).collect(Collectors.toList());
                    context.setAttribute("listOfConversationIds", listOfConversationIds);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return connected;
        }

}
