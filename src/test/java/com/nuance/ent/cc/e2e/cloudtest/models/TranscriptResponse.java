package com.nuance.ent.cc.e2e.cloudtest.models;

import lombok.Data;

@Data
public class TranscriptResponse {
    private String timeStamp;
    private String transcriptValue;
    private Boolean silence;
    private String participantRawId;
}
