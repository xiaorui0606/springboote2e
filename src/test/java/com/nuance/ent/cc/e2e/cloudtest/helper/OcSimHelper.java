package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.nuance.ent.cc.e2e.cloudtest.config.OcSimConfig;
import com.nuance.ent.cc.e2e.cloudtest.models.OcSimReturnTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * The type Oc sim helper.
 */
@Component
@Slf4j
public class OcSimHelper extends TestHelper {

    /**
     * The Acs caller helper.
     */
    @Autowired
    AcsCallerHelper acsCallerHelper;
    @Autowired
    private OcSimConfig ocSimConfig;

    /**
     * Gets oc sim details.
     *
     * @param conversationId the conversation id
     * @return the oc sim details
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public String getOcSimDetails(final String conversationId) throws IOException, InterruptedException {
        HttpRequest httpRequest = ccRequestBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s/%s/%s", ocSimConfig.getBaseMapping(), ocSimConfig.getOcSimMapping(), conversationId)))
                .GET()
                .build();

        String ocSimResponse = httpDispatcher.dispatch(httpRequest);

        return ocSimResponse;
    }

    /**
     * Gets agent info.
     *
     * @param conversationId the conversation id
     * @return the agent info
     */
    public List<Object> getAgentInfo(final String conversationId) {

        List<Object> agentInfos = new ArrayList<>();

        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");
            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object agentInfo = getJsonValueByPath(dataStr, "$.agentInfo");
                    if (agentInfo != null) {
                        agentInfos.add(agentInfo);
                    }
                }
            }
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getAgentInfo(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return null;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getAgentInfo(): Conversation ID " + conversationId + ": " + e.getMessage());
            return null;
        }
        return agentInfos;
    }

    /**
     * Gets activities.
     *
     * @param conversationId the conversation id
     * @return the activities
     */
    public List<Object> getActivities(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");
            return activities;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getActivities(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return null;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getActivities(): Conversation ID " + conversationId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets enrollment trained status.
     *
     * @param conversationId the conversation id
     * @return the enrollment trained status
     */
    public String getEnrollmentTrainedStatus(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");

            log.info("Activities from getEnrollmentTrainedStatus : \n"+activities.toString());
            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object personEvent = getJsonValueByPath(dataStr, "$.personEvent");
                    if (personEvent != null) {
                        String personEventStr = new Gson().toJson(personEvent, LinkedHashMap.class);
                        String enrollStatus = getJsonValueByPath(personEventStr, "$.enrollStatus").toString();
                        if ("ENROLL_STATUS_TRAINED".equals(enrollStatus)) {
                            return OcSimReturnTypes.SUCCESS;
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getEnrollmentTrainedStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getEnrollmentTrainedStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Gets person not enrolled status.
     *
     * @param conversationId the conversation id
     * @return the person not enrolled status
     */
    public String getPersonNotEnrolledStatus(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");

            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object personEvent = getJsonValueByPath(dataStr, "$.personEvent");
                    if (personEvent != null) {
                        String personEventStr = new Gson().toJson(personEvent, LinkedHashMap.class);
                        String enrollStatus = getJsonValueByPath(personEventStr, "$.enrollStatus").toString();
                        if ("ENROLL_STATUS_NOT_ENROLLED".equals(enrollStatus)) {
                            return OcSimReturnTypes.SUCCESS;
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getEnrollmentTrainedStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getEnrollmentTrainedStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Get decision authentic status string.
     *
     * @param conversationId the conversation id
     * @return the string
     */
    public String getDecisionAuthenticStatus(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");
            log.info("Activities from getEnrollmentTrainedStatus : \n" + activities.toString());

            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object sessionDecisionEvent = getJsonValueByPath(dataStr, "$.sessionDecisionEvent");
                    if (sessionDecisionEvent != null) {
                        String sessionDecisionEventStr = new Gson().toJson(sessionDecisionEvent, LinkedHashMap.class);
                        String decisionStatus = (String) getJsonValueByPath(sessionDecisionEventStr, "$.decision");
                        if ("DECISION_AUTHENTIC".equals(decisionStatus)) {
                            return OcSimReturnTypes.SUCCESS;
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getDecisionAuthenticStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getDecisionAuthenticStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Getconversation update status string.
     *
     * @param conversationId the conversation id
     * @return the string
     */
    public String getConversationUpdateStatus(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");
            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                String conversationUpdate = (String) getJsonValueByPath(activityStr, "$.type");
                if ("conversationUpdate".equals(conversationUpdate)) {
                    return OcSimReturnTypes.SUCCESS;
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getconversationUpdateStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getconversationUpdateStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Is gk verification complete string.
     *
     * @param conversationId the conversation id
     * @return the string
     */
    /* Logic Description
     *  ------------------
     * Step1: Get Conversation ID from OCSim Database
     * Step2: For all the activities corresponding to that Conversation, Check if we get ENROLL_STATUS_TRAINED
     * Step3: If we get this status, then only loop into the activities to check if we have DECISION_AUTHENTIC event
     * Step4: If we don't have ENROLL_STATUS_TRAINED, skip the test
     * Step5: If we don't have DECISION_AUTHENTIC status, fail the test
     * Steo6: If we have DECISION_AUTHENTIC status, pass the test
     */
    public String isGkVerificationComplete(String conversationId) {
        String checkStatus = null;

        try {
            String enrollmentstatus = getEnrollmentTrainedStatus(conversationId);
            if (OcSimReturnTypes.SUCCESS.equals(enrollmentstatus)) {
                String decsionAuthenticStatus = getDecisionAuthenticStatus(conversationId);
                if (OcSimReturnTypes.SUCCESS.equals(decsionAuthenticStatus))
                    checkStatus = OcSimReturnTypes.SUCCESS;
                else
                    checkStatus = OcSimReturnTypes.FAILURE;
            } else if (OcSimReturnTypes.FAILURE.equals(enrollmentstatus))
                checkStatus = OcSimReturnTypes.SKIPPED;

            return checkStatus;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method isGkVerificationComplete(): Conversation ID " + conversationId + " is not found for the deployment environment");
            checkStatus = OcSimReturnTypes.FAILURE;
            return checkStatus;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method isGkVerificationComplete(): Conversation ID " + conversationId + ": " + e.getMessage());
            checkStatus = OcSimReturnTypes.FAILURE;
            return checkStatus;
        }
    }

    /**
     * Gets audio validity.
     *
     * @param conversationId the conversation id
     * @return the audio validity
     */
    public String getAudioValidity(final String conversationId) {

        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");

            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object processAudioEvent = getJsonValueByPath(dataStr, "$.processAudioEvent");
                    if (processAudioEvent != null) {
                        String processAudioEventStr = new Gson().toJson(processAudioEvent, LinkedHashMap.class);
                        Object progressEvent = getJsonValueByPath(processAudioEventStr, "$.progressEvent");
                        if (progressEvent != null) {
                            String progressEventstr = new Gson().toJson(progressEvent, LinkedHashMap.class);
                            String audioValidity = (String) getJsonValueByPath(progressEventstr, "$.audioValidity");
                            if ("REASON_AUDIO_OK".equals(audioValidity)) {
                                return OcSimReturnTypes.SUCCESS;
                            }
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getAudioValidity(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getAudioValidity(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Gets enrollment ready status.
     *
     * @param conversationId the conversation id
     * @return the enrollment ready status
     */
    public String getEnrollmentReadyStatus(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");

            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object enrollEvent = getJsonValueByPath(dataStr, "$.enrollEvent");
                    if (enrollEvent != null) {
                        String enrollEventStr = new Gson().toJson(enrollEvent, LinkedHashMap.class);
                        Object completedEvent = getJsonValueByPath(enrollEventStr, "$.completedEvent");
                        if (completedEvent != null) {
                            String completedEventStr = new Gson().toJson(completedEvent, LinkedHashMap.class);
                            Object enrollStatus = getJsonValueByPath(completedEventStr, "$.enrollStatus");
                            if ("ENROLL_STATUS_READY".equals(enrollStatus)) {
                                return OcSimReturnTypes.SUCCESS;
                            }
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getEnrollmentReadyStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getEnrollmentReadyStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Gets status code.
     *
     * @param conversationId the conversation id
     * @return the status code
     */
    public String getStatusCode(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");

            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object status = getJsonValueByPath(dataStr, "$.status");
                    if (status != null) {
                        String statusStr = new Gson().toJson(status, LinkedHashMap.class);
                        Object statusCode = getJsonValueByPath(statusStr, "$.statusCode");
                        if ("OK".equals(statusCode)) {
                            return OcSimReturnTypes.SUCCESS;
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getStatusCode(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getStatusCode(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Gets trained status.
     *
     * @param conversationId the conversation id
     * @return the trained status
     */
    public String getTrainedStatus(final String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");

            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object trainEvent = getJsonValueByPath(dataStr, "$.trainEvent");
                    if (trainEvent != null) {
                        String trainEventStr = new Gson().toJson(trainEvent, LinkedHashMap.class);
                        Object completedEvent = getJsonValueByPath(trainEventStr, "$.completedEvent");
                        if (completedEvent != null) {
                            String completedEventStr = new Gson().toJson(completedEvent, LinkedHashMap.class);
                            Object trainStatus = getJsonValueByPath(completedEventStr, "$.trainStatus");
                            if ("TRAIN_STATUS_TRAINED".equals(trainStatus)) {
                                return OcSimReturnTypes.SUCCESS;
                            }
                        }
                    }
                }
            }
            return OcSimReturnTypes.FAILURE;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getTrainedStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getTrainedStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Is opt in verification complete string.
     *
     * @param conversationId the conversation id
     * @return the string
     */
    public String isOptInVerificationComplete(String conversationId) {
        String checkStatus = null;

        try {
            String audioValidity = getAudioValidity(conversationId);
            if (OcSimReturnTypes.SUCCESS.equals(audioValidity)) {
                String personNotEnrolledStatus = getPersonNotEnrolledStatus(conversationId);
                if (OcSimReturnTypes.SUCCESS.equals(personNotEnrolledStatus)) {
                    String enrollmentReadyStatus = getEnrollmentReadyStatus(conversationId);
                    if (OcSimReturnTypes.SUCCESS.equals(enrollmentReadyStatus)) {
//                        String statusCode = getStatusCode(conversationId);
//                        if (OcSimReturnTypes.SUCCESS.equals(statusCode)) {
                            String trainedStatus = getTrainedStatus(conversationId);
                            if (OcSimReturnTypes.SUCCESS.equals(trainedStatus))
                                checkStatus = OcSimReturnTypes.SUCCESS;
                            else
                                checkStatus = OcSimReturnTypes.FAILURE;
//                        } else
//                            checkStatus = OcSimReturnTypes.FAILURE;
                    } else
                        checkStatus = OcSimReturnTypes.FAILURE;
                } else
                    checkStatus = OcSimReturnTypes.FAILURE;
            } else if (OcSimReturnTypes.FAILURE.equals(audioValidity))
                checkStatus = OcSimReturnTypes.FAILURE;

            return checkStatus;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method isOptInVerificationComplete(): Conversation ID " + conversationId + " is not found for the deployment environment");
            checkStatus = OcSimReturnTypes.FAILURE;
            return checkStatus;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method isOptInVerificationComplete(): Conversation ID " + conversationId + ": " + e.getMessage());
            checkStatus = OcSimReturnTypes.FAILURE;
            return checkStatus;
        }
    }

    /**
     * Gets json value by path.
     *
     * @param response the response
     * @param jsonPath the json path
     * @return the json value by path
     */
    public Object getJsonValueByPath(final String response, final String jsonPath) {
        try {
            var result = JsonPath.read(response, jsonPath);
            return result;
        } catch (PathNotFoundException e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Gets oc sim call completed status.
     *
     * @param conversationId the conversation id
     * @return the oc sim call completed status
     */
    public String getOcSimCallCompletedStatus(String conversationId) {
        try {
            String ocSim = getOcSimDetails(conversationId);
            List<Object> activities = (List) getJsonValueByPath(ocSim, "$[0].activities");
            for (Object activity : activities) {
                String activityStr = new Gson().toJson(activity, LinkedHashMap.class);
                Object data = getJsonValueByPath(activityStr, "$.data");
                if (data != null) {
                    String dataStr = new Gson().toJson(data, LinkedHashMap.class);
                    Object ctiEvent = getJsonValueByPath(dataStr, "$.ctiEvent");

                    if (ctiEvent != null) {
                        String ctiEventStr = new Gson().toJson(ctiEvent, LinkedHashMap.class);
                        String eventType = (String) getJsonValueByPath(ctiEventStr, "$.eventType");

                        if ("CALL_ENDED".equals(eventType)) {
                            return OcSimReturnTypes.SUCCESS;
                        }
                    }
                }
            }
            return OcSimReturnTypes.SKIPPED;
        } catch (NoSuchElementException e) {
            Reporter.log("Exception Originated in the method getEnrollmentReadyStatus(): Conversation ID " + conversationId + " is not found for the deployment environment");
            return OcSimReturnTypes.FAILURE;
        } catch (Exception e) {
            Reporter.log("Exception Originated in the method getEnrollmentReadyStatus(): Conversation ID " + conversationId + ": " + e.getMessage());
            return OcSimReturnTypes.FAILURE;
        }
    }

    /**
     * Verify test session ended boolean.
     *
     * @param testSessionId the test session id
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean verifyTestSessionEnded(final String testSessionId) throws Exception {
        LinkedList<String> statusList = new LinkedList<>();
        var callList = acsCallerHelper.getConversationIdsFromTestSessionId(testSessionId);
        callList.forEach(callId -> {
            String ocSimCallStatus = getOcSimCallCompletedStatus(callId);
            statusList.add(ocSimCallStatus);
        });
        return statusList.stream().allMatch(c -> c.equals(OcSimReturnTypes.SUCCESS));
    }

    /**
     * Verify oc opt in for test session boolean.
     *
     * @param testSessionId the test session id
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean verifyOcOptInForTestSession(final String testSessionId) throws Exception {
        LinkedList<String> statusList = new LinkedList<>();
        var callIdList = acsCallerHelper.getConversationIdsFromTestSessionId(testSessionId);
        callIdList.forEach(callId -> {
            statusList.add(isOptInVerificationComplete(callId));
        });
        return statusList.stream().allMatch(c -> c.equals(OcSimReturnTypes.SUCCESS));
    }
}
