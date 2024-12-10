package com.nuance.ent.cc.e2e.cloudtest.helper;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * The type Person test data.
 */
@Component
public class PersonTestData {
    /**
     * The constant PLACE_CALL_POST_REQUEST_FILE_PATH.
     */
    public static final String PERSON_POST_REQUEST_FILE_PATH = "src/test/resources/cloudtest/testdata/person/person.json";

    /**
     * Gets place call post request.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param shortName the short name
     * @return the place call post request
     * @throws Exception the exception
     */
    public String getPersonPostRequest(final String firstName,
                                       final String lastName,
                                       final String shortName) throws Exception {
        return String.format(Locale.ROOT, Files.readString(Paths.get(PERSON_POST_REQUEST_FILE_PATH)), firstName, lastName, shortName);
    }
}
