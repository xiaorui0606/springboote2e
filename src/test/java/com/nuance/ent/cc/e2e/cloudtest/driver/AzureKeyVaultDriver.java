package com.nuance.ent.cc.e2e.cloudtest.driver;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.certificates.CertificateClient;
import com.azure.security.keyvault.certificates.CertificateClientBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.nuance.ent.cc.e2e.cloudtest.config.KeyVaultConfig;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Locale;


/**
 * The type Azure key vault driver.
 */
@Profile(" cloudtest")
@Component
@Getter
public class AzureKeyVaultDriver {

    /**
     * config for the AKV.
     */
    private final KeyVaultConfig keyVaultConfig;
    /**
     * environment bean.
     */
    private final Environment env;
    /**
     * read secrets from AKV.
     */
    private SecretClient secretClient;
    private CertificateClient certificateClient;

    /**
     * Instantiates a new Azure key vault driver.
     *
     * @param keyVaultConfig the key vault config
     * @param env            the env
     */
    public AzureKeyVaultDriver(final KeyVaultConfig keyVaultConfig, final Environment env) {
        this.keyVaultConfig = keyVaultConfig;
        this.env = env;
    }

    /**
     * Init method.
     */
    @PostConstruct
    public void init() {
        final String keyVaultName = keyVaultConfig.getKeyvaultname();
        final String uaiclientid = keyVaultConfig.getUaiclientid();
        final String keyVaultUri = String.format(Locale.ROOT, "https://%s.vault.azure.net", keyVaultName);
        secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().managedIdentityClientId(uaiclientid).build())
                .buildClient();
        certificateClient = new CertificateClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().managedIdentityClientId(uaiclientid).build())
                .buildClient();

    }

    /**
     * Gets key from vault.
     *
     * @param secretKey the secret name
     * @return the key from vault
     */
    public String getValueFromVault(final String secretKey) {
        String envValue = env.getProperty(secretKey);
        // Allow overriding secret value with environment variables
        return StringUtils.hasText(envValue) ? envValue : secretClient.getSecret(secretKey).getValue();
    }

}
