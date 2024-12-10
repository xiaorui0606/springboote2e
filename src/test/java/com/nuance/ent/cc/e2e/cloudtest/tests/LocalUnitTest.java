package com.nuance.ent.cc.e2e.cloudtest.tests;

import com.nuance.ent.cc.e2e.cloudtest.BaseCloudTest;
import com.nuance.ent.cc.e2e.cloudtest.helper.OcSimHelper;
import com.nuance.ent.cc.e2e.cloudtest.models.OcSimReturnTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * The type Basic test.
 */
@Slf4j
public class LocalUnitTest extends BaseCloudTest {

    /**
     * The Oc sim helper.
     */
    @Autowired
    OcSimHelper ocSimHelper;

    /**
     * Test one.
     */
    @Test
    public void testOne() {
        log.info("executed");
    }

    /**
     * Check oc sim record status.
     */
    @Test
    public void checkOcSimRecordStatus() {
        String conversationId = "ce3b23b6-9fc7-413a-a5f5-a2b59ec2333a";
        List<Object> agentInfoList = ocSimHelper.getAgentInfo(conversationId);
        Integer agentInfoCount = agentInfoList.size();
        assert agentInfoCount > 0;
    }

    /**
     * Check activities record status.
     */
    @Test
    public void checkActivitiesRecordStatus() {
        String conversationId = "ce3b23b6-9fc7-413a-a5f5-a2b59ec2333a";
        List<Object> activtiesList = ocSimHelper.getActivities(conversationId);
        Integer activitiesCount = activtiesList.size();
        assert activitiesCount > 0;
    }

    /**
     * Test positive update conversation status.
     */
    @Test
    public void testPositiveUpdateConversationStatus() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.getConversationUpdateStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }

    /**
     * Test positive enrollment trained status.
     */
    @Test
    public void testPositiveEnrollmentTrainedStatus() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.getEnrollmentTrainedStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }

    /**
     * Test negetive enrollment trained status.
     */
    @Test
    public void testNegetiveEnrollmentTrainedStatus() {
        String conversationId = "f479e9f3-f7e6-4f12-874e-51ae6fd7d2c7";
        String checkStatus = ocSimHelper.getEnrollmentTrainedStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.FAILURE);
    }

    /**
     * Test positive enrollment not enrolled status.
     */
    @Test
    public void testPositiveEnrollmentNotEnrolledStatus() {
        String conversationId = "f479e9f3-f7e6-4f12-874e-51ae6fd7d2c7";
        String checkStatus = ocSimHelper.getPersonNotEnrolledStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }

    /**
     * Test negetive enrollment not enrolled status.
     */
    @Test
    public void testNegetiveEnrollmentNotEnrolledStatus() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.getPersonNotEnrolledStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.FAILURE);
    }

    /**
     * Test positive decision authentic status.
     */
    @Test
    public void testPositiveDecisionAuthenticStatus() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.getDecisionAuthenticStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }

    /**
     * Test positive gk verification complete.
     */
    @Test
    public void testPositiveGkVerificationComplete() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.isGkVerificationComplete(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }

    /**
     * Test negetive gk verification complete.
     */
    @Test
    public void testNegetiveGkVerificationComplete() {
        String conversationId = "7dcb75f5-447d-4fac-a618-5ac458b15a6c";
        String checkStatus = ocSimHelper.isGkVerificationComplete(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.FAILURE);
    }

    /**
     * Test skipped gk verification complete.
     */
    @Test
    public void testSkippedGkVerificationComplete() {
        String conversationId = "f479e9f3-f7e6-4f12-874e-51ae6fd7d2c7";
        String checkStatus = ocSimHelper.isGkVerificationComplete(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SKIPPED);
    }

    /**
     * Test positive opt in verification complete.
     */
    @Test
    public void testPositiveOptInVerificationComplete() {
        String conversationId = "f479e9f3-f7e6-4f12-874e-51ae6fd7d2c7";
        String checkStatus = ocSimHelper.isOptInVerificationComplete(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }

    /**
     * Test negative opt in verification complete.
     */
    @Test
    public void testNegativeOptInVerificationComplete() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.isOptInVerificationComplete(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.FAILURE);
    }

    /**
     * Test positive oc sim call completed status.
     */
    @Test
    public void testPositiveOcSimCallCompletedStatus() {
        String conversationId = "d5030464-e5d7-4f48-9543-d2954a66d3f3";
        String checkStatus = ocSimHelper.getOcSimCallCompletedStatus(conversationId);
        Assert.assertEquals(checkStatus, OcSimReturnTypes.SUCCESS);
    }
}
