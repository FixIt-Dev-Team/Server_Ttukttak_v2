package com.backend.ttukttak_v2.config.auth.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * OAuth 인증 성공 시 처리 핸들러
 */
@Slf4j
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${oauth.token-url}")
    private String OAUTH_TOKEN_URL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    /**
     * OAuth 인증 성공 시 처리
     * TODO 추후 다른 소셜 로그인 방식이 추가될떄 수정 필요
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Oauth Success");

        String userEmail = ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("email");

        UriComponentsBuilder redirectUri = UriComponentsBuilder.fromUriString(OAUTH_TOKEN_URL)
                .queryParam("email", userEmail);
        response.sendRedirect(redirectUri.toUriString());
    }
}
