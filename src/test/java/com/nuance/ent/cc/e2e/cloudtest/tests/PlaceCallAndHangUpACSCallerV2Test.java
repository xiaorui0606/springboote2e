package com.nuance.ent.cc.e2e.cloudtest.tests;

import com.nuance.ent.cc.e2e.cloudtest.BaseCloudTest;
import com.nuance.ent.cc.e2e.cloudtest.config.ServicePrincipalConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestEnvConfig;
import com.nuance.ent.cc.e2e.cloudtest.driver.AcsDriver;
import com.nuance.ent.cc.e2e.cloudtest.driver.AzureKeyVaultDriver;
import com.nuance.ent.cc.e2e.cloudtest.helper.AcsV3CallerHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.PersonHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.TestHelper;
import com.nuance.ent.cc.e2e.cloudtest.utility.RetryUtil;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.*;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class PlaceCallAndHangUpACSCallerV2Test extends BaseCloudTest {

    private static String sourceNumber = "+";
    private static String targetNumber = "+";
    private String testSessionId;
    protected RetryUtil<Boolean> retryUtil = new RetryUtil<>();
    public static String access_token;
    protected static boolean msgFound;
    public static Properties config = new Properties();
    public static FileInputStream fis;
    private static String[] dtmfOptions = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "asterisk", "pound"};
    @Autowired
    AcsDriver acsDriver;
    /**
     * The Acs caller helper.
     */
    @Autowired
    AcsV3CallerHelper acsCallerHelper;
    /**
     * The Person helper.
     */
    @Autowired
    PersonHelper personHelper;

    /**
     * The Azure key vault driver.
     */
    @Autowired
    protected AzureKeyVaultDriver azureKeyVaultDriver;

    /**
     * The Test Env config.
     */
    @Autowired
    protected TestEnvConfig testEnvConfig;

    /**
     * The Service Principal config config.
     */
    @Autowired
    protected ServicePrincipalConfig spConfig;

    @BeforeClass
    public void setUp() {
        access_token = getToken();
        sourceNumber = sourceNumber + testAcsConfig.getSourceNumber();
        targetNumber = targetNumber + testAcsConfig.getTargetNumber();
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

    @AfterClass
    public void cleanup() throws Exception {
        if (testSessionId != null) {
            acsCallerHelper.callHangUp(testSessionId, access_token);
        }
    }

    /**
     * task-723490: Automation of happy path
     */
    @Test()
    public void placeCallUsingV3Test() throws Exception {
        //Start the call
        testSessionId = acsCallerHelper.placeCallV3(targetNumber, sourceNumber, 600, "en-US", access_token);
        System.out.println(testSessionId);
        //Assert that proper Caller events triggered
        TestHelper.sleep(4);
        Response response = acsCallerHelper.getCallerEventsResponse(testSessionId, access_token);
        JSONArray jsonArray = new JSONArray(response.body().asString());
        String responseBody = jsonArray.getJSONObject(0).getString("eventMessage");
        System.out.println("From Caller events : " + responseBody);
        Assert.assertEquals(responseBody, "Test session started");
        //wait for call to get properly connected
        TestHelper.sleep(4);
        //Assert that call connected and participants are 2
        Assert.assertEquals(acsCallerHelper.getTotalParticipants(testSessionId, access_token), 2);
        //Get the Conversation id
        var callSessionIdList = acsCallerHelper.getConversationIdsFromTestSessionId(testSessionId, access_token);
        System.out.println(callSessionIdList.get(0));
        // Get Transcript data to assert Welcome prompt
        msgFound = retryUtil.pollDuration(20, 5000l).method(() -> acsCallerHelper.getTranscriptResponse(testSessionId, access_token, config.getProperty("welcomeMsg"))).setRetryPolicy(res -> res == Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        //Send DTMF tone
        Assert.assertEquals(acsCallerHelper.sendDTMFUsingTestSessionId(testSessionId, access_token, dtmfOptions[1]), 202);
        // Get transcript data to assert message sent by HM after C2 sent dtmf tone
        msgFound=retryUtil.pollDuration(25, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("travelToQuestion"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        // Speak TTS request
        Assert.assertEquals(acsCallerHelper.speakTTSUsingTestSessionId(testSessionId, access_token, config.getProperty("travelToAnswer")), "success");
        // Get transcript data to assert message sent by HM after C2 sent speak tts
        msgFound=retryUtil.pollDuration(25, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("seatAvailableToBook"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        // C2 confirms yes
        Assert.assertEquals(acsCallerHelper.speakTTSUsingTestSessionId(testSessionId, access_token, config.getProperty("confirmYesVoice")), "success");
        // Get transcript data to assert booking confirmation message from HM
        msgFound=retryUtil.pollDuration(25, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("flightBookedMsg"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        //Get transcript to assert go back to menu option
        msgFound=retryUtil.pollDuration(25, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("backToDtmfMenu"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        //Send DTMF tone
        Assert.assertEquals(acsCallerHelper.sendDTMFUsingTestSessionId(testSessionId, access_token, dtmfOptions[10]), 202);
        //Get transcript to assert go back to menu option
        msgFound=retryUtil.pollDuration(28, 7000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("dtmfPromptStart"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        msgFound=retryUtil.pollDuration(20, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("dtmfPromptEnd"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        // C2 confirms yes
        Assert.assertEquals(acsCallerHelper.speakTTSUsingTestSessionId(testSessionId, access_token, config.getProperty("flightDetailsVoice")), "success");
        // Get transcript data to assert booking confirmation message from HM
        msgFound=retryUtil.pollDuration(28, 7000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("flightStatusMsg"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        //Get transcript to assert go back to menu option
        msgFound=retryUtil.pollDuration(28, 7000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("backToDtmfMenu"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        //Send DTMF tone
        Assert.assertEquals(acsCallerHelper.sendDTMFUsingTestSessionId(testSessionId, access_token, dtmfOptions[4]), 202);
        //Get transcript to assert goodbye message
        msgFound=retryUtil.pollDuration(28, 7000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("goodbyeMsg"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        // Hang up the call
        //Assert.assertEquals(acsCallerHelper.callHangUp(testSessionId,access_token),"success");
    }

    /**
     test case if 729554 : [DTMF Menu, Barge-in off] DTMF input at the end of menu prompt, success
     */
    @Test
    public void test_729554() throws Exception {
        testSessionId = acsCallerHelper.placeCallV3(targetNumber,sourceNumber,600, "en-US",access_token);
        System.out.println(testSessionId);
        //Assert that proper Caller events triggered
        TestHelper.sleep(4);
        Response response = acsCallerHelper.getCallerEventsResponse(testSessionId, access_token);
        JSONArray jsonArray = new JSONArray(response.body().asString());
        String responseBody = jsonArray.getJSONObject(0).getString("eventMessage");
        System.out.println("From Caller events : " + responseBody);
        Assert.assertEquals(responseBody, "Test session started");
        //wait for call to get properly connected
        TestHelper.sleep(4);
        //Assert that call connected and participants are 2
        Assert.assertEquals(acsCallerHelper.getTotalParticipants(testSessionId, access_token), 2);
        //Get the Conversation id
        var callSessionIdList = acsCallerHelper.getConversationIdsFromTestSessionId(testSessionId, access_token);
        System.out.println(callSessionIdList.get(0));
        // Get Transcript data to assert Welcome prompt
        msgFound = retryUtil.pollDuration(20, 5000l).method(() -> acsCallerHelper.getTranscriptResponse(testSessionId, access_token, config.getProperty("welcomeMsg"))).setRetryPolicy(res -> res == Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        msgFound=retryUtil.pollDuration(20, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("dtmfPromptStart"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        msgFound=retryUtil.pollDuration(20, 5000l).method(()-> acsCallerHelper.getTranscriptResponse(testSessionId,access_token,config.getProperty("dtmfPromptEnd"))).setRetryPolicy(res->res==Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);
        //Send DTMF tone
        Assert.assertEquals(acsCallerHelper.sendDTMFUsingTestSessionId(testSessionId, access_token, dtmfOptions[1]), 202);
        // Get transcript data to assert message sent by HM after C2 sent dtmf tone
        msgFound = retryUtil.pollDuration(20, 5000l).method(() -> acsCallerHelper.getTranscriptResponse(testSessionId, access_token, config.getProperty("travelToQuestion"))).setRetryPolicy(res -> res == Boolean.TRUE).execute();
        Assert.assertTrue(msgFound);

    }

}
