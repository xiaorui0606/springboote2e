package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.nuance.ent.cc.e2e.cloudtest.config.AcsCallerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * The type Acs caller test data.
 */
@Component
public class AcsCallerTestData {
    /**
     * The constant PLACE_CALL_POST_REQUEST_FILE_PATH.
     */
    public static final String PLACE_CALL_POST_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/acscaller/placecall.json";
    public static final String PLACE_CALL_V3_POST_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/acscaller/placecallV3.json";
    public static final String HANGUP_CALL_POST_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/acscaller/hangupcall.json";
    public static final String SEND_DTMF_POST_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/acscaller/senddtmf.json";
    public static final String SPEAK_TTS_POST_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/acscaller/speaktts.json";
    /**
     * The Acs caller config.
     */
    @Autowired
    AcsCallerConfig acsCallerConfig;
    /**
     * The Audio test data.
     */
    @Autowired
    AudioTestData audioTestData;

    /**
     * Gets place call post request.
     *
     * @param destinationNumber the destination number
     * @param sourceId          the source id
     * @param audioLoopTimeOut  the audio loop time out
     * @param audioFileName     the audio file name
     * @param connectionString  the connection string
     * @param personShortName   the person short name
     * @return the place call post request
     * @throws Exception the exception
     */
    public String getPlaceCallPostRequest(final String destinationNumber,
                                          final String sourceId,
                                          final int audioLoopTimeOut,
                                          final String audioFileName,
                                          final String connectionString,
                                          final String personShortName) throws Exception {
        return String.format(Locale.ROOT, Files.readString(Paths.get(PLACE_CALL_POST_REQUEST_FILE_PATH)), destinationNumber, sourceId, audioLoopTimeOut, audioTestData.getAudioFileUrl(audioFileName), connectionString, personShortName);
    }

    public String getPlaceCallPostRequestV3(final String destinationNumber,
                                          final String sourceNumber,
                                          final int callHangUpTimeout,
                                          final String connectionString,
                                          final String language
                                          ) throws Exception {
        return String.format(Locale.ROOT, Files.readString(Paths.get(PLACE_CALL_V3_POST_REQUEST_FILE_PATH)), destinationNumber, sourceNumber, callHangUpTimeout, connectionString, language);
    }

    public String getCallHangUpPostRequest(final String testSessionId, final String connString, final String callHangUpIdType) throws IOException {
        return String.format(Locale.ROOT, Files.readString(Paths.get(HANGUP_CALL_POST_REQUEST_FILE_PATH)),testSessionId,connString,callHangUpIdType);
    }

    public String postSendDTMFRequest(final String testSessionId, final String dtmfTone, final String idType, final String connString) throws IOException {
        return String.format(Locale.ROOT, Files.readString(Paths.get(SEND_DTMF_POST_REQUEST_FILE_PATH)) ,testSessionId, dtmfTone, idType, connString);
    }

    public String postSpeakTTSRequest(final String testSessionId, final String idType, final String connString, final String speechText) throws IOException {
        return String.format(Locale.ROOT, Files.readString(Paths.get(SPEAK_TTS_POST_REQUEST_FILE_PATH)) ,testSessionId, idType, connString, speechText);
    }

}
