package com.nuance.ent.cc.e2e.cloudtest.helper;

import com.nuance.ent.cc.e2e.cloudtest.config.ServicePrincipalConfig;
import com.nuance.ent.cc.e2e.cloudtest.config.TestEnvConfig;
import com.nuance.ent.cc.e2e.cloudtest.driver.AzureKeyVaultDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Audio test data.
 */
@Slf4j
@Component
public class AudioTestData {
    /**
     * The Service principal config.
     */
    @Autowired
    ServicePrincipalConfig servicePrincipalConfig;
    /**
     * The Test env config.
     */
    @Autowired
    TestEnvConfig testEnvConfig;
    /**
     * The Key vault driver.
     */
    @Autowired
    AzureKeyVaultDriver keyVaultDriver;

    /**
     * Gets audio file url.
     *
     * @param audioFileName the audio file name
     * @return the audio file url
     */
    public String getAudioFileUrl(final String audioFileName) {
        return String.format(Locale.ROOT, "%s/%s%s", testEnvConfig.getStorageAccountUrl(), audioFileName, keyVaultDriver.getValueFromVault(servicePrincipalConfig.getAudioSas()));
    }
}
