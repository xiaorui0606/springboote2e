package com.nuance.ent.cc.e2e.cloudtest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Acs caller config.
 */
@Component
@ConfigurationProperties(prefix = "acscaller")
@Data
public class AcsCallerConfig extends TestEnvConfig {
    private String baseUrl;
    private String placeCallMapping;
    private String testSessionMapping;
    private String genericCallEventsMapping;
    private String acsCallEventsMapping;
    private String conversationFromTestMapping;
    private String personMapping;
    private String acsClientId;
    private String acsClientSk;
    private String callHangUpMapping;
    private String v3CallerEventsMapping;
    private String sendDTMFMapping;
    private String getTranscriptMapping;
    private String speakTTSMapping;
    private String testSessionStatusMapping;
    private String testSessionSummaryMapping;


    /**
     * Gets place call mapping.
     *
     * @return the place call mapping
     */
    public String getPlaceCallMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), placeCallMapping);
    }

    /**
     * Gets test session mapping mapping.
     *
     * @return the test session mapping mapping
     */
    public String getTestSessionMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), testSessionMapping);
    }

    /**
     * Get conversation from test mapping string.
     *
     * @return the string
     */
    public String getConversationFromTestMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), conversationFromTestMapping);
    }

    /**
     * Get generic call events mapping string.
     *
     * @return the string
     */
    public String getGenericCallEventsMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), genericCallEventsMapping);
    }

    /**
     * Get acs call events mapping string.
     *
     * @return the string
     */
    public String getAcsCallEventsMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), acsCallEventsMapping);
    }

    /**
     * Gets hang Up call mapping.
     *
     * @return the HangUp call mapping
     */
    public String getCallHangUpMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), callHangUpMapping);
    }

    /**
     * Gets Acs caller V3 baseURL mapping.
     *
     * @return the baseURL mapping
     */
    public String getACSBaseURLMapping() {
        return String.format(Locale.ROOT, "%s", this.getBaseUrl());
    }

    /**
     * Gets Acs caller V3 Caller events mapping.
     *
     * @return the Caller events mapping
     */
    public String getACSV3CallerEventsMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), v3CallerEventsMapping);
    }

    /**
     * gets send DTMF call mapping.
     *
     * @return the send DTMF call mapping
     */
    public String getSendDTMFCallMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), sendDTMFMapping);
    }

    /**
     * gets transcript mapping.
     *
     * @return the transcript call mapping
     */
    public String getTranscriptMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), getTranscriptMapping);
    }

    /**
     * gets speak tts call mapping.
     *
     * @return the send DTMF call mapping
     */
    public String getSpeakTTSCallMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), speakTTSMapping);
    }

    /**
     * Gets test session status mapping mapping.
     *
     * @return the test session status mapping
     */
    public String getTestSessionStatusMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), testSessionStatusMapping);
    }

    /**
     * Gets test session summary mapping mapping.
     *
     * @return the test session summary mapping
     */
    public String getTestSessionSummaryMapping() {
        return String.format(Locale.ROOT, "%s%s", this.getBaseUrl(), testSessionSummaryMapping);
    }

}
