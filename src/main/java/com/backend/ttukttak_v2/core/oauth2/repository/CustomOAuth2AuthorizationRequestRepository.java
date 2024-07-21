package com.backend.ttukttak_v2.core.oauth2.repository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

/**
 * OAuth2 로그인 관련 인증 리퀘스트 레포지토리
 */
@Component
public class CustomOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .clientId(GOOGLE_CLIENT_ID)
                .redirectUri("http://127.0.0.1:5201/login/oauth2/code/google")
                .scope(request.getParameter(OAuth2ParameterNames.SCOPE))
                .attributes((attributes) -> {
                    attributes.put(OAuth2ParameterNames.RESPONSE_TYPE, "code");
                    attributes.put(OAuth2ParameterNames.REGISTRATION_ID, "google");
                    attributes.put(OAuth2ParameterNames.CLIENT_ID, GOOGLE_CLIENT_ID);
                })
                .state(request.getParameter(OAuth2ParameterNames.STATE))
                .build();

        return oAuth2AuthorizationRequest;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return loadAuthorizationRequest(request);
    }
}
