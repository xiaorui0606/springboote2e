package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Arm config.
 */
@Component
@ConfigurationProperties(prefix = "armconfig")
@Data
public class ArmConfig {
    /**
     * The Grant type.
     */
    String grantType;
    /**
     * The Resource.
     */
    String resource;
    /**
     * The Arm base url.
     */
    String armBaseUrl;
}
