package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuance.ent.cc.e2e.cloudtest.config.ArmConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestEnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * The type Subscription validation test data.
 */
@Component
public class SubscriptionValidationTestData {

    /**
     * The constant SUBSCRIPTION_VALIDATION_REQUEST_FILE_PATH.
     */
    public static final String WEBHOOK_WITH_SUBJECT_FILTERING_REQUEST = "src/test/resources/cloudtest/testdata/webhook_with_subject_filtering_request_body.json";
    /**
     * The constant WEBHOOK_REQUEST.
     */
    public static final String WEBHOOK_REQUEST = "src/test/resources/cloudtest/testdata/webhook_request_body.json";
    private static final String WEBHOOK_NAME = "AUTOMATION-CC-WEBHOOK";
    @Autowired
    private ArmConfig armConfig;
    @Autowired
    private TestEnvConfig testEnvConfig;


    /**
     * Gets subscription validation request.
     *
     * @param acsRoutingId    the acs routing id
     * @param subjectEndsWith the subject ends with
     * @return the subscription validation request
     * @throws Exception the exception
     */
    public String getSubscriptionValidationPayload(final String acsRoutingId, final String subjectEndsWith) throws Exception {
        final String url = testEnvConfig.getCallControllerUrl();
        final String appId = testEnvConfig.getAzureActiveDirectoryApplicationIdOrUri();
        final String tenant = testEnvConfig.getTenantId();
        if (StringUtils.hasText(subjectEndsWith)) {
            return String.format(Locale.ROOT, Files.readString(Paths.get(WEBHOOK_WITH_SUBJECT_FILTERING_REQUEST)), url, acsRoutingId, appId, tenant,
                    subjectEndsWith);
        } else {
            return String.format(Locale.ROOT, Files.readString(Paths.get(WEBHOOK_REQUEST)), url, acsRoutingId, appId, tenant);
        }
    }


    /**
     * Gets sv url.
     *
     * @param randomId the random id
     * @return the sv url
     */
    public String getSVUrl(final String randomId) {
        String url = "%s/subscriptions/%s/resourceGroups/%s/providers/Microsoft.Communication/CommunicationServices/%s/providers/Microsoft.EventGrid/eventSubscriptions/%s?api-version=2022-06-15";
        return String.format(Locale.ROOT, url, armConfig.getArmBaseUrl(), testEnvConfig.getTestSubscriptionId(),
                testEnvConfig.getTestResourceGroup(), testEnvConfig.getTestAcsInstanceName(), WEBHOOK_NAME + "-" + randomId);
    }

    /**
     * Gets sv deployment status.
     *
     * @param responseBody the response body
     * @return the sv deployment status
     */
    public String getSVDeploymentStatus(final String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        try {
            return jsonObject.get("properties").getAsJsonObject().get("provisioningState").getAsString();
        } catch (Exception e) {
            return "Failed";
        }
    }

}
