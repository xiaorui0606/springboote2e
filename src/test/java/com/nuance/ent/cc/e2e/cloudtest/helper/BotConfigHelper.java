package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.nuance.ent.cc.e2e.cloudtest.config.OcSimConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestEnvConfig;
import com.nuance.ent.cc.e2e.cloudtest.driver.HttpDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Locale;

/**
 * Abstract creation and deletion of BotConfigs.
 */
@Profile(" cloudtest")
@Component
@Slf4j
public class BotConfigHelper extends TestHelper {
    private final BotConfigTestData botConfigTestData;
    private final TestEnvConfig testEnvConfig;
    private final OcSimConfig ocSimConfig;


    /**
     * Instantiates a new Bot config helper.
     *
     * @param httpDispatcher    the http dispatcher
     * @param botConfigTestData the bot config test data
     * @param ccRequestBuilder  the cc request builder
     * @param testEnvConfig     the test env config
     * @param ocSimConfig       the oc sim config
     */
    public BotConfigHelper(final HttpDispatcher httpDispatcher,
                           final BotConfigTestData botConfigTestData,
                           final HttpRequest.Builder ccRequestBuilder,
                           final TestEnvConfig testEnvConfig,
                           final OcSimConfig ocSimConfig) {
        this.httpDispatcher = httpDispatcher;
        this.botConfigTestData = botConfigTestData;
        this.ccRequestBuilder = ccRequestBuilder;
        this.testEnvConfig = testEnvConfig;
        this.ocSimConfig = ocSimConfig;
    }


    /**
     * Create bot config bot config test data . bot data.
     *
     * @param acsUserId the acs user id
     * @param botId     the bot id
     * @return routingId bot config test data . bot data
     * @throws Exception the exception
     */
    public BotConfigTestData.BotData createBotConfig(final String acsUserId, final String botId) throws Exception {

        var requestBody = botConfigTestData.getBotConfigPutRequest(acsUserId, testEnvConfig.getTestResourceGroup(), testEnvConfig.getTestAcsInstanceName(), botId, String.format(Locale.ROOT, "%s/%s",ocSimConfig.getBaseMapping(), ocSimConfig.getOcSimMapping()));
        log.info("Request Body: {}", requestBody);
        HttpRequest botConfigRequest = ccRequestBuilder
                .uri(URI.create(botConfigTestData.getBotConfigEndpoint(testEnvConfig.getBotControllerUrl(), botId)))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        log.info("Creating BotConfig: {}", botConfigRequest.uri());
        String responseBody = httpDispatcher.dispatch(botConfigRequest);
        log.info("Response Body: {}", responseBody);
        BotConfigTestData.BotData botData = botConfigTestData.mapToBotConfig(responseBody);
        log.info("Bot Data: {}", botData);
        log.info("Created routingId: {}", botData.acsRoutingId);
        return botData;
    }

    /**
     * Delete bot config.
     *
     * @param botId the bot id
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
//Check:OFF: MagicNumber
    public void deleteBotConfig(final String botId) throws IOException, InterruptedException {
        HttpRequest request = ccRequestBuilder
                .uri(URI.create(botConfigTestData.getBotConfigEndpoint(testEnvConfig.getBotControllerUrl(), botId)))
                .DELETE()
                .build();
        log.info("Deleting BotConfig: {}", request.uri());
        httpDispatcher.dispatch(request, List.of(204));
    }
}
