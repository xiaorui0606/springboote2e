package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Credentials for the cloud test service principal in AKV.
 */
@Component
@ConfigurationProperties(prefix = "serviceprincipal")
@Data
public class ServicePrincipalConfig {
    /**
     * key for clientId in AKV.
     */
    private String clientIdKey;
    /**
     * key for clientSecret in AKV.
     */
    private String clientSecretKey;
    private String audioSas;
    private String acsClientId;
    private String acsClientSk;
}
