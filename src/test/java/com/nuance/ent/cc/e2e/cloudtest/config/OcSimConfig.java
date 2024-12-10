package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Oc sim config.
 */
@Component
@ConfigurationProperties(prefix = "ocsim")
@Data
public class OcSimConfig {

    private String baseMapping;
    private String ocSimMapping;
    private Integer callCompletionTimeoutSeconds;
}
