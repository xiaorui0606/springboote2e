package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Key vault configuration.
 */
@Component
@ConfigurationProperties(prefix = "azkeyvault")
@Data
public class KeyVaultConfig {
    private String keyvaultname;
    private String secretname;
    private String uaiclientid;
    }
