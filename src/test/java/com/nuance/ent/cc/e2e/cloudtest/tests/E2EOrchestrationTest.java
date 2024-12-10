package com.nuance.ent.cc.e2e.cloudtest.tests;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.nuance.ent.cc.e2e.cloudtest.BaseCloudTest;
import com.nuance.ent.cc.e2e.cloudtest.driver.AcsDriver;
import com.nuance.ent.cc.e2e.cloudtest.helper.AcsCallerHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.BotConfigHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.OcSimHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.PersonHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.TestHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.WebHookHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


/**
 * The type Incoming call test.
 */
public class E2EOrchestrationTest extends BaseCloudTest {
    private static final String AUDIO_FILE_NAME = "DJ_SAMPLE_AUDIO_JASON_16KHZ_M1.wav";
    private static final int AUDIO_LOOP_TIME_OUT = 60;
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
     * The Oc sim helper.
     */
    @Autowired
    OcSimHelper ocSimHelper;
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
    private String testSessionId;

    /**
     * Sets .
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public void setup() throws Exception {
        callSource = acsDriver.createAcsUser();
        callTarget = acsDriver.createAcsUser();
        setupBotConfig();
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
//    @BeforeMethod
    public void setupBotConfig() throws Exception {
        randomId = UUID.randomUUID().toString();
        botId = "CTEST-BOT-ID-" + randomId;
        var targetUserId = callTarget.getRawId();
        var botData = botConfigHelper.createBotConfig(targetUserId, botId);
        webHookHelper.createWebHookWithSubjectFiltering(botData.getAcsRoutingId(), targetUserId, randomId);
        TestHelper.sleep(5); // Allow webhook to be properly created
    }

    /**
     * Test acs call.
     *
     * @throws Exception the exception
     */
    @Test(groups = {"E2EOrchestrationTest.testIncomingCall"})
    public void testIncomingCall() throws Exception {
        testSessionId = acsCallerHelper.placeCall(callTarget.getRawId(), callSource.getRawId(), AUDIO_FILE_NAME, personHelper.getPersonShortName(), AUDIO_LOOP_TIME_OUT);
        Assert.assertTrue(acsCallerHelper.waitForCallConnected(testSessionId));
        Assert.assertEquals(acsCallerHelper.getTotalParticipants(testSessionId), 2);
        Assert.assertTrue(acsCallerHelper.waitForTestSessionHangUp(testSessionId));
    }

    /**
     * Verify opt in.
     *
     * @throws Exception the exception
     */
// @Test
    @Test(groups = "E2EOrchestrationTest.verifyOptIn", dependsOnGroups = {"E2EOrchestrationTest.testIncomingCall"})
    public void verifyOptIn() throws Exception {
        Assert.assertTrue(ocSimHelper.verifyTestSessionEnded(testSessionId));
        Assert.assertTrue(ocSimHelper.verifyOcOptInForTestSession(testSessionId));
    }

    /**
     * Verify gk engagement.
     *
     * @throws Exception the exception
     */
    @Test(groups = "E2EOrchestrationTest.verifyGkEngagement", dependsOnGroups = {"E2EOrchestrationTest.verifyOptIn"})
    public void verifyGkEngagement() throws Exception {
        ArrayList<String> statusList = new ArrayList<>();
        var testSessionId = acsCallerHelper.placeCall(callTarget.getRawId(), callSource.getRawId(), AUDIO_FILE_NAME, personHelper.getPersonShortName(), AUDIO_LOOP_TIME_OUT);
        Assert.assertTrue(acsCallerHelper.waitForCallConnected(testSessionId));
        acsCallerHelper.waitForTestSessionHangUp(testSessionId);
        var responseList = acsCallerHelper.getConversationIdsFromTestSessionId(testSessionId);
        Assert.assertTrue(ocSimHelper.verifyTestSessionEnded(testSessionId));
        for (String conversationId : responseList) {
            String statusValue = ocSimHelper.isGkVerificationComplete(conversationId);
            statusList.add(statusValue);
        }
        Assert.assertFalse(statusList.contains("FAILURE"));
    }
}
