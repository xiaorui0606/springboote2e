package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Test acs config.
 */
@Component
@ConfigurationProperties(prefix = "testacs")
@Data
public class TestAcsConfig {
    private String connectionStringKey;
    private String sourceNumber;
    private String targetNumber;

    public String getSourceNumber() {
        return sourceNumber;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

}
