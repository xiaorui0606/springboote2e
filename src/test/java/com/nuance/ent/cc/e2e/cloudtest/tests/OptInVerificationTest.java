package com.nuance.ent.cc.e2e.cloudtest.tests;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.nuance.ent.cc.e2e.cloudtest.BaseCloudTest;
import com.nuance.ent.cc.e2e.cloudtest.driver.AcsDriver;
import com.nuance.ent.cc.e2e.cloudtest.helper.AcsCallerHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.BotConfigHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.EventTypes;
import com.nuance.ent.cc.e2e.cloudtest.helper.OcSimHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.PersonHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.TestHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.WebHookHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * The type Opt in verification test.
 */

public class OptInVerificationTest extends BaseCloudTest {

    private static final String AUDIO_FILE_NAME = "DJ_SAMPLE_AUDIO_JASON_16KHZ_M1.wav";
    private static final int AUDIO_LOOP_TIME_OUT = 30;
    /**
     * The Bot config helper.
     */
    @Autowired
    BotConfigHelper botConfigHelper;

    /**
     * The Web hook helper.
     */
    @Autowired
    WebHookHelper webHookHelper;

    /**
     * The Oc sim helper.
     */
    @Autowired
    OcSimHelper ocSimHelper;

    /**
     * The Acs driver.
     */
    @Autowired
    AcsDriver acsDriver;
    /**
     * The Acs caller helper.
     */
    @Autowired
    AcsCallerHelper acsCallerHelper;
    /**
     * The Person helper.
     */
    @Autowired
    PersonHelper personHelper;
    /**
     * The Call source.
     */
    CommunicationUserIdentifier callSource;
    /**
     * The Call target.
     */
    CommunicationUserIdentifier callTarget;
    /**
     * The Random id.
     */
    String randomId;
    /**
     * The Bot id.
     */
    String botId;

    /**
     * The Test helper.
     */
    TestHelper testHelper;

    /**
     * Sets opt in.
     *
     * @throws Exception the exception
     */

    /**
     * Sets .
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public void setup() throws Exception {
        callSource = acsDriver.createAcsUser();
        callTarget = acsDriver.createAcsUser();
    }

    /**
     * Cleanup.
     *
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    @AfterClass
    public void cleanup() throws IOException, InterruptedException {
        acsDriver.deleteAcsUser(callSource);
        acsDriver.deleteAcsUser(callTarget);
        webHookHelper.deleteWebHook(randomId);
        botConfigHelper.deleteBotConfig(botId);
    }

    /**
     * Sets bot config.
     *
     * @throws Exception the exception
     */
    @BeforeMethod
    public void setupBotConfig() throws Exception {
        randomId = UUID.randomUUID().toString();
        botId = "CTEST-BOT-ID-" + randomId;
        var targetUserId = callTarget.getRawId();
        var botData = botConfigHelper.createBotConfig(targetUserId, botId);
        webHookHelper.createWebHookWithSubjectFiltering(botData.getAcsRoutingId(), targetUserId, randomId);
        TestHelper.sleep(5); // Allow webhook to be properly created
    }

    /**
     * Verify OptIn engagement.
     *
     * @throws Exception the exception
     */
    @Test
    public void verifyOptIn() throws Exception {
        ArrayList<String> statusList = new ArrayList<>();
        var testSessionId = acsCallerHelper.placeCall(callTarget.getRawId(), callSource.getRawId(), AUDIO_FILE_NAME, personHelper.getPersonShortName(), AUDIO_LOOP_TIME_OUT);
        var responseList = acsCallerHelper.getConversationIdsFromTestSessionId(testSessionId);
        for (String conversationId : responseList) {
            var genericCallEvents = acsCallerHelper.checkGenericCallerEvents(conversationId);
            for (HashMap<String, String> genericCallEvent : genericCallEvents) {
                if (!genericCallEvent.get("eventMessage").equals(EventTypes.CALL_HANGUP)) {
                    Thread.sleep(1000);
                    continue;
                }
            }
            String statusValue = ocSimHelper.isOptInVerificationComplete(conversationId);
            statusList.add(statusValue);
        }
        if(statusList.contains("SKIPPED")){
            Reporter.log("User/s is/are not opted in. Hence skipping the test");
            throw new SkipException("User/s is/are not opted in. Hence skipping the test");
        }

        Assert.assertEquals(statusList.contains("FAILURE"), false);
    }
}
