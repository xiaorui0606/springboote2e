package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.nuance.ent.cc.e2e.cloudtest.config.AcsCallerConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Locale;
import java.util.UUID;

/**
 * The type Person helper.
 */
@Component
@Getter
@Slf4j
public class PersonHelper extends TestHelper {
    @Autowired
    @Qualifier("acsRequestBuilder")
    private HttpRequest.Builder personApiBuilder;
    @Autowired
    private AcsCallerConfig acsCallerConfig;
    @Autowired
    private PersonTestData personTestData;

    private String personShortName;
    private String personFirstName;
    private String personLastName;

    /**
     * Set person short name.
     */
    public void setPersonShortName() {
        this.personShortName = String.format(Locale.ROOT, "e2etest-%s", UUID.randomUUID());
    }

    /**
     * Set person first name.
     */
    public void setPersonFirstName() {
        this.personFirstName = String.format(Locale.ROOT, "e2etest-fn-%s", UUID.randomUUID());
    }

    /**
     * Set person last name.
     */
    public void setPersonLastName() {
        this.personLastName = String.format(Locale.ROOT, "e2etest-ln-%s", UUID.randomUUID());
    }

    /**
     * Create person string.
     *
     * @return the string
     * @throws Exception the exception
     */
    public String createPerson() throws Exception {

        this.setPersonLastName();
        this.setPersonFirstName();
        this.setPersonShortName();

        HttpRequest personRequest = personApiBuilder
                .uri(URI.create(String.format(Locale.ROOT, "%s%s", acsCallerConfig.getBaseUrl(), acsCallerConfig.getPersonMapping())))
                .POST(HttpRequest.BodyPublishers.ofString(personTestData.getPersonPostRequest(this.personFirstName, this.personLastName, this.personShortName)))
                .build();
        log.info("Generating Person with short name: " + personShortName);
        String responseBody = httpDispatcher.dispatch(personRequest).toString();
        log.info("Person created: {}", responseBody);
        return responseBody;
    }
}
