package com.msft.onecall.client;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.msft.onecall.client")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.msft.onecall.client")
public class RunCucumberTest {
    private static final Logger logger = LoggerFactory.getLogger(RunCucumberTest.class);
    static String rerunScenarios = "";

    @After
    public void afterTest(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.info("**************** After Test for failed scenario ****************");
            String scenarioName = scenario.getName();
            String[] scenarioNameSplit = scenarioName.trim().split(":");
            String testcaseId = scenarioNameSplit[0].trim();

            if (rerunScenarios.equals("")) {
                rerunScenarios = "@" + testcaseId;
            } else {
                rerunScenarios = rerunScenarios + " or " + "@" + testcaseId;
            }
            logger.info("**************** End of After Test for failed scenario ****************");
        }
    }

    @AfterAll
    public static void afterAll() throws IOException {
        logger.info("After all Tests to rerun = " + rerunScenarios);
        FileWriter fWriter = new FileWriter(
                "./src/test/resources/failedScenarios.txt");
        fWriter.write(rerunScenarios);
        fWriter.close();
        System.out.println(
                "File is created successfully with the failed rerun tests.");
    }
}
