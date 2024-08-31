package com.backend.ttukttak_v2.config.jwt;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.core.auth.application.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtAuthenticationProcessingFilter
 * <p>
 * <p>
 * "/login" 이외의 모든 요청에 대해 Jwt 토큰을 검증하는 필터
 * <p>
 * <p>
 * 1. RefreshToken이 없고, AccessToken이 유효한 경우 <br></br>
 * - 인증 성공 처리, RefreshToken을 재발급하지는 않는다. <br></br>
 * 2. RefreshToken이 없고, AccessToken이 없거나 유효하지 않은 경우  <br></br>
 * - 인증 실패 처리, 403 ERROR <br></br>
 * 3. RefreshToken이 있는 경우 <br></br>
 * - DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급 <br></br>
 * - 인증 성공 처리는 하지 않고 실패 처리 및 403 ERROR <br></br> ,.ㅡ
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String AUTH_URL_PATTERN = "/api/auth";
    private final List<String> OAUTH_URL = List.of(
            "/oauth2/authorization/google",
            "/login/oauth2/code/google",
            "/test/mvc"
    );
    private final JwtService jwtService;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 체크하지 않는 URL인지 확인
        if (checkUncheckURI(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("Start Processing Jwt Filter : " + request.getRequestURI());
        String refreshToken = request.getHeader("Refresh");

        // AccessToken 유효 체크
        long userIdx = -1;
        try {
            userIdx = jwtService.validateAccess(request.getHeader(HttpHeaders.AUTHORIZATION));
            filterChain.doFilter(request, response);
            request.setAttribute("userIdx", userIdx);
            return;
        } catch (BaseException exception) {
            log.info("Exception Thrown: " + exception.getMessage());
            log.info("Access Token Invalid! Try Refresh Access Token....");
        }

        // AccessToken이 유효하지 않다면 Refresh Token 존재 여부 체크
        if (refreshToken.isEmpty()) {
            throw BaseException.of(ErrorCode.UNAUTHORIZED);
        }

        // Refresh Token이 있다면 DB와 비교 - 불일치 시 Auth Fail 내려줌
        if (!authService.checkRefreshToken(refreshToken, userIdx)) {
            throw BaseException.of(ErrorCode.UNAUTHORIZED);
        } else {
            throw BaseException.of(ErrorCode.TOKEN_REFRESH_REQUIRED);
        }
    }


    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    private boolean checkUncheckURI(HttpServletRequest request) {
        return request.getRequestURI().matches(AUTH_URL_PATTERN + ".*") || OAUTH_URL.contains(request.getRequestURI());
    }
}
