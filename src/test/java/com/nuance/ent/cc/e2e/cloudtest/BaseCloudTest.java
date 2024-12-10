package com.nuance.ent.cc.e2e.cloudtest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.nuance.ent.cc.e2e.cloudtest.config.*;
import com.nuance.ent.cc.e2e.cloudtest.driver.ApiDriver;
import com.nuance.ent.cc.e2e.cloudtest.driver.AzureKeyVaultDriver;
import com.nuance.ent.cc.e2e.cloudtest.helper.AcsCallerHelper;
import com.nuance.ent.cc.e2e.cloudtest.helper.PersonHelper;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Base cloud test.
 */
@SpringBootTest(classes = BaseCloudTest.CloudTestContext.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Profile("cloudtest")
@Slf4j
@SuppressWarnings("VisibilityModifier")
public class BaseCloudTest extends AbstractTestNGSpringContextTests {

    /**
     * The Api driver.
     */
    @Autowired
    protected ApiDriver apiDriver;
    /**
     * The Test env config.
     */
    @Autowired
    protected TestEnvConfig testEnvConfig;
    /**
     * The Webclient.
     */
    @Autowired
    protected HttpClient webclient;
    /**
     * The Arm config.
     */
    @Autowired
    protected ArmConfig armConfig;

    /**
     * The Person helper.
     */
    @Autowired
    protected PersonHelper personHelper;

    /**
     * The Acs caller config.
     */
    @Autowired
    protected AcsCallerConfig acsCallerConfig;

    /**
     * The Acs caller helper.
     */
    @Autowired
    protected AcsCallerHelper acsCallerHelper;

    /**
     * The Gkcc blocking stub.
     */

    @Autowired
    protected TestAcsConfig testAcsConfig;

    /**
     * The Azure key vault driver.
     */
    @Autowired
    protected AzureKeyVaultDriver azureKeyVaultDriver;

    /**
     * The Service Principal config config.
     */
    @Autowired
    protected ServicePrincipalConfig spConfig;

//     /**
//     * Before suite.
//     *
//     * @throws Exception the exception
//     */
//    @PostConstruct
//    public void beforeSuite() throws Exception {
//        personHelper.createPerson();
//    }

    public static String access_token;

    public String getToken() {

        String reqBody = String.format(Locale.ROOT, "client_id=%s&client_secret=%s&grant_type=%s&scope=%s",
                azureKeyVaultDriver.getValueFromVault(spConfig.getClientIdKey()),
                azureKeyVaultDriver.getValueFromVault(spConfig.getClientSecretKey()),
                testEnvConfig.getCcOauthGrantType(), testEnvConfig.getCcOauthScope());

        String authTokenUrl = testEnvConfig.getCcOauthTokenUrl();
        Response response = given().contentType("application/x-www-form-urlencoded").body(reqBody).get(authTokenUrl);
        access_token = response.jsonPath().get("access_token").toString();
        return access_token;
    }

    /**
     * The type Cloud test context.
     */
    @SpringBootApplication(scanBasePackages = "com.nuance.ent.cc")
    @SuppressWarnings("MagicNumber")
    public static class CloudTestContext {
        /**
         * Web client http client.
         *
         * @return the http client
         */
        @Bean
        HttpClient webClient() {
            return HttpClient.newBuilder()
                    .connectTimeout(Duration.of(5, ChronoUnit.SECONDS))
                    .build();
        }


        /**
         * Cc request builder http request . builder.
         *
         * @param webclient           the webclient
         * @param azureKeyVaultDriver the azure key vault driver
         * @param testEnvConfig       the test env config
         * @param spConfig            the sp config
         * @return the http request . builder
         * @throws IOException               the io exception
         * @throws InterruptedException      the interrupted exception
         * @throws UnrecoverableKeyException the unrecoverable key exception
         * @throws CertificateException      the certificate exception
         * @throws NoSuchAlgorithmException  the no such algorithm exception
         * @throws KeyStoreException         the key store exception
         * @throws NoSuchProviderException   the no such provider exception
         * @throws ExecutionException        the execution exception
         */
        @Profile("cloudtest")
        @Bean("ccRequestBuilder")
        @Autowired
        HttpRequest.Builder ccRequestBuilder(final HttpClient webclient,
                                             final AzureKeyVaultDriver azureKeyVaultDriver,
                                             final TestEnvConfig testEnvConfig,
                                             final ServicePrincipalConfig spConfig) throws IOException, InterruptedException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, ExecutionException {
            if (testEnvConfig.getUseFirstPartyAppAuth().equalsIgnoreCase("true")) {
                var certName = testEnvConfig.getCertificateName();
                var passString = "";
                var scope = testEnvConfig.getCcOauthScope();
                var ccOauthTokenUrl = testEnvConfig.getCcOauthTokenUrl();
                var clientId = azureKeyVaultDriver.getValueFromVault(spConfig.getClientIdKey());
                return ccFirstPartyAuthRequestBuilder(certName, passString, scope, ccOauthTokenUrl, clientId, azureKeyVaultDriver);
            }

            final String ccTokenRequestBody = String.format(Locale.ROOT, "client_id=%s&client_secret=%s&grant_type=%s&scope=%s",
                    azureKeyVaultDriver.getValueFromVault(spConfig.getClientIdKey()),
                    azureKeyVaultDriver.getValueFromVault(spConfig.getClientSecretKey()),
                    testEnvConfig.getCcOauthGrantType(), testEnvConfig.getCcOauthScope());
            var ccTokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(testEnvConfig.getCcOauthTokenUrl()))
                    .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(ccTokenRequestBody)).build();

            return getOauthRequestBuilder(webclient, ccTokenRequest);
        }

        /**
         * Arm request builder http request . builder.
         *
         * @param webclient           the webclient
         * @param azureKeyVaultDriver the azure key vault driver
         * @param testEnvConfig       the test env config
         * @param spConfig            the sp config
         * @param armConfig           the arm config
         * @return the http request . builder
         * @throws IOException          the io exception
         * @throws InterruptedException the interrupted exception
         */
        @Profile("cloudtest")
        @Bean("armRequestBuilder")
        @Autowired
        HttpRequest.Builder armRequestBuilder(HttpClient webclient,
                                              AzureKeyVaultDriver azureKeyVaultDriver,
                                              TestEnvConfig testEnvConfig,
                                              ServicePrincipalConfig spConfig,
                                              ArmConfig armConfig) throws IOException, InterruptedException {

            final String armTokenRequestBody = String.format(Locale.ROOT, "client_id=%s&client_secret=%s&grant_type=%s&resource=%s",
                    azureKeyVaultDriver.getValueFromVault(spConfig.getClientIdKey()),
                    azureKeyVaultDriver.getValueFromVault(spConfig.getClientSecretKey()),
                    armConfig.getGrantType(),
                    armConfig.getResource());
            var armTokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(testEnvConfig.getOAuthTokenUrl()))
                    .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(armTokenRequestBody))
                    .build();
            return getOauthRequestBuilder(webclient, armTokenRequest);
        }

        /**
         * Acs request builder http request . builder.
         *
         * @param webclient           the webclient
         * @param azureKeyVaultDriver the azure key vault driver
         * @param acsConfig           the acs config
         * @return the http request . builder
         * @throws IOException          the io exception
         * @throws InterruptedException the interrupted exception
         */
        @Profile("cloudtest")
        @Bean("acsRequestBuilder")
        @Autowired
        HttpRequest.Builder acsRequestBuilder(final HttpClient webclient,
                                              final AzureKeyVaultDriver azureKeyVaultDriver,
                                              final AcsCallerConfig acsConfig) throws IOException, InterruptedException {

            final String ccTokenRequestBody = String.format(Locale.ROOT, "client_id=%s&client_secret=%s&grant_type=%s&scope=%s",
                    azureKeyVaultDriver.getValueFromVault(acsConfig.getAcsClientId()),
                    azureKeyVaultDriver.getValueFromVault(acsConfig.getAcsClientSk()),
                    acsConfig.getCcOauthGrantType(), acsConfig.getCcOauthScope());
            var ccTokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(acsConfig.getCcOauthTokenUrl()))
                    .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(ccTokenRequestBody)).build();
            return getOauthRequestBuilder(webclient, ccTokenRequest);
        }

        /**
         * Gets oauth request builder.
         *
         * @param webclient    the webclient
         * @param tokenRequest the token request
         * @return the oauth request builder
         * @throws IOException          the io exception
         * @throws InterruptedException the interrupted exception
         */
        HttpRequest.Builder getOauthRequestBuilder(final HttpClient webclient,
                                                   final HttpRequest tokenRequest) throws IOException, InterruptedException {
            var response = webclient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            final String authToken = jsonObject.get("access_token").getAsString();
            return HttpRequest.newBuilder().header(AUTHORIZATION, String.format(Locale.ROOT, "Bearer %s", authToken)).header(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        }

        private HttpRequest.Builder ccFirstPartyAuthRequestBuilder(final String certName, final String passString,
                                                                   final String scope, final String ccOauthTokenUrl,
                                                                   final String clientId, final AzureKeyVaultDriver azureKeyVaultDriver) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchProviderException, ExecutionException, InterruptedException {
            try {
                var secret = azureKeyVaultDriver.getSecretClient().getSecret(certName);
                var b64secret = Base64.getDecoder().decode(secret.getValue());
                InputStream certStream = new ByteArrayInputStream(b64secret);
                ClientCredentialParameters clientCredentialParam = ClientCredentialParameters.builder(
                                Collections.singleton(scope))
                        .build();
                ConfidentialClientApplication app = ConfidentialClientApplication.builder(
                                clientId,
                                ClientCredentialFactory.createFromCertificate(certStream, passString))
                        .authority(ccOauthTokenUrl)
                        .build();
                CompletableFuture<IAuthenticationResult> future = app.acquireToken(clientCredentialParam);
                var t = future.get();
                log.info("access token acquired successfully");
                return HttpRequest.newBuilder().header(AUTHORIZATION, String.format(Locale.ROOT, "Bearer %s", t.accessToken())).header(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            } catch (Exception e) {
                log.error("error while acquiring auth token through 1st party app and certificate: {}", e.getMessage());
                throw e;
            }
        }
    }
}
