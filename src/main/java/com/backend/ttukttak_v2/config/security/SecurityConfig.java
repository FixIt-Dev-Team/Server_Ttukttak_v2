package com.backend.ttukttak_v2.config.security;

import com.backend.ttukttak_v2.config.handler.auth.OAuthFailureHandler;
import com.backend.ttukttak_v2.config.handler.auth.OAuthSuccessHandler;
import com.backend.ttukttak_v2.config.security.filter.JwtAuthenticationFilter;
import com.backend.ttukttak_v2.config.web.CorsConfig;
import com.backend.ttukttak_v2.core.auth.application.service.AuthService;
import com.backend.ttukttak_v2.core.oauth2.repository.CustomOAuth2AuthorizationRequestRepository;
import com.backend.ttukttak_v2.core.oauth2.repository.InMemoryRegistrationRepository;
import com.backend.ttukttak_v2.core.oauth2.service.CustomOAuthUserService;
import com.backend.ttukttak_v2.framework.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final CustomOAuthUserService oAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final OAuthFailureHandler oAuthFailureHandler;
    private final JwtService jwtService;
    private final AuthService authService;
    private final CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository;
    private final InMemoryRegistrationRepository inMemoryRegistrationRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * SecurityFilterChain 빈 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .logout(AbstractHttpConfigurer::disable)
                .headers(header ->
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers("/api/auth/**").permitAll();
                            request.requestMatchers("/oauth2/authorization/**").permitAll();
                            request.requestMatchers("/login/oauth2/code/**").permitAll();
                            request.requestMatchers("/error").permitAll();
                            request.requestMatchers("/favicon.ico").permitAll();
                            request.requestMatchers("/test/**").permitAll();
                            request.anyRequest().authenticated();
                        }
                )
                .oauth2Login((oauth2) -> oauth2
                        .authorizationEndpoint(
                                (authorization) ->
                                        authorization.authorizationRequestRepository(customOAuth2AuthorizationRequestRepository)
                        )
                        .clientRegistrationRepository(inMemoryRegistrationRepository)
                        .userInfoEndpoint(
                                (userInfo) -> userInfo.userService(oAuthUserService)
                        )
                        .successHandler(oAuthSuccessHandler)
                        .failureHandler(oAuthFailureHandler)
                )
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                .build();
    }
}
