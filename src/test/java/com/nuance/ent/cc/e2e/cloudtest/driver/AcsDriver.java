package com.nuance.ent.cc.e2e.cloudtest.driver;

import com.azure.communication.callautomation.CallAutomationClient;
import com.azure.communication.callautomation.CallAutomationClientBuilder;
import com.azure.communication.callautomation.CallConnection;
import com.azure.communication.callautomation.models.CallConnectionProperties;
import com.azure.communication.callautomation.models.CallConnectionState;
import com.azure.communication.callautomation.models.CallSource;
import com.azure.communication.callautomation.models.CreateCallOptions;
import com.azure.communication.callautomation.models.CreateCallResult;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.nuance.ent.cc.e2e.cloudtest.config.TestAcsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Helper for placing calls to ACS.
 */
//Check:OFF: MagicNumber
@Profile("cloudtest")
@Component
@Slf4j
public class AcsDriver {

    private final AzureKeyVaultDriver keyVaultDriver;
    private final TestAcsConfig testAcsConfig;
    private final Duration timeOut = Duration.of(30, ChronoUnit.SECONDS);
    private CommunicationIdentityClient identityClient;
    private CallAutomationClient callAutomationClient;

    /**
     * Instantiates a new Acs driver.
     *
     * @param keyVaultDriver the key vault driver
     * @param testAcsConfig  the test acs config
     */
    public AcsDriver(final AzureKeyVaultDriver keyVaultDriver, final TestAcsConfig testAcsConfig) {
        this.keyVaultDriver = keyVaultDriver;
        this.testAcsConfig = testAcsConfig;
    }

    /**
     * Initialisation.
     */
    @PostConstruct
    public void init() {
        String connectionString = keyVaultDriver.getValueFromVault(testAcsConfig.getConnectionStringKey());
        callAutomationClient = new CallAutomationClientBuilder().connectionString(connectionString).buildClient();
        identityClient = new CommunicationIdentityClientBuilder().connectionString(connectionString).buildClient();
    }

    /**
     * Start call call connection.
     *
     * @param caller the caller
     * @param target the target
     * @return the CallConnection
     * @throws InterruptedException the interrupted exception
     */
    public CallConnection startCall(final CommunicationUserIdentifier caller, final CommunicationUserIdentifier target) throws InterruptedException {
        CreateCallOptions callOptions = new CreateCallOptions(new CallSource(caller), List.of(target), "https://localhost/events");
        Response<CreateCallResult> createCallResponse = callAutomationClient.createCallWithResponse(callOptions, Context.NONE);
        log.info("CorrelationId: {}", createCallResponse.getHeaders().get("X-Microsoft-Skype-Chain-ID"));
        return waitForCallConnection(createCallResponse.getValue());
    }

    private CallConnection waitForCallConnection(final CreateCallResult result) throws InterruptedException {
        CallConnection callConnection = result.getCallConnection();
        Instant deadline = Instant.now().plus(timeOut);
        while (Instant.now().isBefore(deadline)) {
            CallConnectionProperties callProperties = callConnection.getCallProperties();
            log.info("Call status: {}", callProperties.getCallConnectionState());
            if (callProperties.getCallConnectionState() == CallConnectionState.CONNECTED) {
                log.info("Call connection established!");
                break;
            }
            Thread.sleep(1000);
        }
        return callConnection;
    }

    /**
     * Creates a acs user.
     *
     * @return the created acs user handle.
     */
    public CommunicationUserIdentifier createAcsUser() {
        return identityClient.createUser();
    }

    /**
     * Delete acs user.
     *
     * @param user the user
     */
    public void deleteAcsUser(final CommunicationUserIdentifier user) {
        if (user != null) {
            log.info("Deleting acs user {}", user.getId());
            identityClient.deleteUser(user);
        }
    }

}
