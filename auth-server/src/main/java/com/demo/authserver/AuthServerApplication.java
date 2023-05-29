package com.demo.authserver;

import com.demo.authserver.service.JpaRegisteredClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * AuthServerApplication
 */
@SpringBootApplication
public class AuthServerApplication implements CommandLineRunner {

    private final JpaRegisteredClientRepository jpaRegisteredClientRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServerApplication(JpaRegisteredClientRepository jpaRegisteredClientRepository, PasswordEncoder passwordEncoder) {
        this.jpaRegisteredClientRepository = jpaRegisteredClientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        RegisteredClient adminClient = getClient();

        jpaRegisteredClientRepository.deleteByClientId(adminClient.getClientId());

        jpaRegisteredClientRepository.save(adminClient);
    }

    /**
     * Create a RegisteredClient object
     *
     * @return RegisteredClient
     */
    private RegisteredClient getClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-admin")
                .clientName("client-admin")
                .clientIdIssuedAt(Instant.now())
                .clientSecret(passwordEncoder.encode("admin-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8083/login/oauth2/code/client-oidc")
                .redirectUri("http://127.0.0.1:8083/authorized")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("client.create")
                .scope("client.update")
                .scope("client.delete")
                .scope("client.read")
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30L)).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
    }
}
