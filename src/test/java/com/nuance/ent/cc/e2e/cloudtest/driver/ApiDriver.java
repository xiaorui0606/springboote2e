package com.nuance.ent.cc.e2e.cloudtest.driver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * The type Api driver.
 */
@Component
@Data
public class ApiDriver {

    private SessionFilter sessionFilter;
    private SessionConfig sessionConfig;
    private RestAssuredConfig restAssuredConfig;
    private RequestSpecBuilder requestSpecBuilder;
    private RequestSpecification requestSpecification;

    /**
     * Create and get rest assured config rest assured config.
     *
     * @return the rest assured config
     */
    public RestAssuredConfig createAndGetRestAssuredConfig() {
        restAssuredConfig = RestAssured.config();
        sessionConfig = new SessionConfig();
        restAssuredConfig.sessionConfig(sessionConfig);
        return restAssuredConfig;
    }

    /**
     * Create and build request spec request specification.
     *
     * @param baseUrl the base url
     * @return the request specification
     */
    public RequestSpecification createAndBuildRequestSpec(final String baseUrl) {
        sessionFilter = new SessionFilter();
        requestSpecBuilder = new RequestSpecBuilder().addFilter(sessionFilter).setBaseUri(baseUrl);
        requestSpecification = requestSpecBuilder.build();
        return requestSpecification;
    }

}
