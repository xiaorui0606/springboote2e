package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * The type Bot config test data.
 */
@Component
public class BotConfigTestData {
    /**
     * The constant BOT_CONFIG_PUT_REQUEST_FILE_PATH.
     */
    public static final String BOT_CONFIG_PUT_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/bot/bot_config_request.json";

    /**
     * The constant INVALID.
     */
    public static final String INVALID = "INVALID";


    /**
     * format string for BotConfig endpoint on BotController.
     */
    public static final String BOT_CONFIG_ENDPOINT_FORMAT = "%s/settings/v1/bot/%s";


    /**
     * Gets bot config put request with a specific acs user set.
     *
     * @param acsUserId the acs user id
     * @param botId     the bot id
     * @param ocSimUrl  the oc sim url
     * @return the bot config put request
     * @throws Exception the exception
     */
    public String getBotConfigPutRequest(final String acsUserId, final String testAcsRg, final String testAcsInstanceName, final String botId, String ocSimUrl) throws Exception {
        return String.format(Locale.ROOT, Files.readString(Paths.get(BOT_CONFIG_PUT_REQUEST_FILE_PATH)), acsUserId, testAcsRg,testAcsInstanceName, botId, ocSimUrl);
    }

    /**
     * Map json to BotConfigData.
     *
     * @param responseBody the response body
     * @return the acs routing id from bot config
     * @throws JsonProcessingException the json processing exception
     */
    public BotData mapToBotConfig(final String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper.readValue(responseBody, BotData.class);
    }

    /**
     * Gets bot config endpoint.
     *
     * @param botControllerUrl the bot controller url
     * @param botId            the bot id
     * @return endpoint for upserting a BotConfig.
     */
    public String getBotConfigEndpoint(final String botControllerUrl, final String botId) {
        return String.format(Locale.ROOT, BOT_CONFIG_ENDPOINT_FORMAT, botControllerUrl, botId);
    }


    /**
     * The type Bot data.
     */
    @Data
    public static class BotData {

        /**
         * The Acs routing id.
         */
        public String acsRoutingId;
        /**
         * The Acs recipient id.
         */
        public String acsRecipientId;
    }
}
