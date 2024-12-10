package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;


/**
 * The type Test env config.
 */
@Component
@ConfigurationProperties(prefix = "cloudtest")
@Data
public class TestEnvConfig {
    /**
     * The constant URL_FORMAT.
     */
    public static final String URL_FORMAT = "%s%s";
    private static final String DEFAULT_URL = "https://login.windows.net/72f988bf-86f1-41af-91ab-2d7cd011db47/oauth2/token";
    private static final String DEFAULT_CC_OAUTH_SCOPE = "https://microsoft.onmicrosoft.com/255ed4e5-aa54-4fdb-a0de-ed692fd4eb3a/.default";
    private static final String DEFAULT_CC_OAUTH_URL = "https://login.microsoftonline.com/72f988bf-86f1-41af-91ab-2d7cd011db47/oauth2/v2.0/token";
    /**
     * The Base url.
     */
    String baseUrl;
    /**
     * The Call controller mapping.
     */
    String callControllerMapping;
    /**
     * The Bot controller mapping.
     */
    String botControllerMapping;
    /**
     * The Gkcc mapping.
     */
    String gkccMapping;
    /**
     * The Tenant id.
     */
    String tenantId;
    /**
     * The Test subscription id.
     */
    String testSubscriptionId;
    /**
     * The Test resource group.
     */
    String testResourceGroup;
    /**
     * The Test acs instance name.
     */
    String testAcsInstanceName;
    /**
     * The Azure active directory application id or uri.
     */
    String azureActiveDirectoryApplicationIdOrUri;
    /**
     * The Auth Token url.
     */
    String oAuthTokenUrl;
    /**
     * The Cc oauth token url.
     */
    String ccOauthTokenUrl;
    /**
     * The Cc oauth scope.
     */
    String ccOauthScope;
    /**
     * The Cc oauth grant type.
     */
    String ccOauthGrantType;
    /**
     * The Storage account url.
     */
    String storageAccountUrl;
    /**
     * The Certificate name.
     */
    String certificateName;
    /**
     * The Use first party app auth.
     */
    String useFirstPartyAppAuth;

    /**
     * Gets call controller url.
     *
     * @return the call controller url
     */
    public String getCallControllerUrl() {
        return StringUtils.hasText(callControllerMapping) ? String.format(Locale.ROOT, URL_FORMAT, baseUrl, callControllerMapping) : baseUrl;
    }


    /**
     * Gets bot controller url.
     *
     * @return the bot controller url
     */
    public String getBotControllerUrl() {
        return StringUtils.hasText(botControllerMapping) ? String.format(Locale.ROOT, URL_FORMAT, baseUrl, botControllerMapping) : baseUrl;
    }


    /**
     * Gets gkcc url.
     *
     * @return the gkcc url
     */
    public String getGkccUrl() {
        return StringUtils.hasText(gkccMapping) ? String.format(Locale.ROOT, URL_FORMAT, baseUrl, gkccMapping) : baseUrl;
    }

    /**
     * Gets arm login url.
     *
     * @return the Oauth token url
     */
    public String getOAuthTokenUrl() {
        return StringUtils.hasText(tenantId) ? String.format(Locale.ROOT, oAuthTokenUrl, tenantId) : DEFAULT_URL;
    }

    /**
     * Gets cc oauth scope.
     *
     * @return the cc oauth scope
     */
    public String getCcOauthScope() {
        return StringUtils.hasText(azureActiveDirectoryApplicationIdOrUri) ? String.format(Locale.ROOT, ccOauthScope, azureActiveDirectoryApplicationIdOrUri) : DEFAULT_CC_OAUTH_SCOPE;
    }
    /**
     * Gets cc oauth token url.
     *
     * @return the cc oauth token url
     */
    public String getCcOauthTokenUrl() {
        return StringUtils.hasText(tenantId) ? String.format(Locale.ROOT, ccOauthTokenUrl, tenantId) : DEFAULT_CC_OAUTH_URL;
    }
}
