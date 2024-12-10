package com.nuance.ent.cc.e2e.cloudtest.tests;



import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ScenarioDataProvider {

    @DataProvider(name = "scenarios")
    public Object[][] getScenarios() {
        return new Object[][]{
                {"getAccessToken",null,null,null,null},
                {"placeCallXML", "+18552465844", "+18772151378", "600", "en-US"},
                {"getTestSessionStatus",null,null,null,null},
                {"getCallerEvents",null,null,null,null},
                {"getTranscriptResponseUsingPolling", "Contoso Airlines.", "0.75", "25", "5000"},
                {"getTranscriptResponseUsingPolling", "Press 1 to book a flight. Press 2 to check your flight status. Press 3 for a lost luggage. Press 4 to hang up or press Asterisk to hear this menu again.",
                        "0.75", "25", "5000"},
                {"waitingForPreviousProcessToComplete", "3",null,null,null}
        };
    }

}