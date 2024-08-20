package com.backend.ttukttak_v2.core.oauth2.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

/**
 * Registration 관련 레포지토리
 */
@Component
public class InMemoryRegistrationRepository implements ClientRegistrationRepository {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;
    @Value("${spring.security.oauth2.client.registration.google.client-name}")
    private String GOOGLE_CLIENT_NAME;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return getRegistration(registrationId);

    }

    private ClientRegistration getRegistration(String registrationId) {
        if (registrationId.equals("google")) {
            return ClientRegistration.withRegistrationId(registrationId)
                    .clientId(GOOGLE_CLIENT_ID)
                    .clientSecret(GOOGLE_CLIENT_SECRET)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(GOOGLE_REDIRECT_URI)
                    .scope("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile")
                    .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                    .tokenUri("https://oauth2.googleapis.com/token")
                    .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .userNameAttributeName(IdTokenClaimNames.SUB)
                    .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                    .clientName(GOOGLE_CLIENT_NAME)
                    .build();
        }
        return null;
    }
}
