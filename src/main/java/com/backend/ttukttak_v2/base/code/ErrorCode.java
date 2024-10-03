package com.backend.ttukttak_v2.base.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 400 BAD_REQUEST
     */
    BAD_REQUEST(HttpStatusCode.valueOf(400), "COMMON_4000", "Bad request"),
    OAUTH_BAD_PROVIDER(HttpStatusCode.valueOf(400), "OAUTH_4000", "유효하지않은 OAuth2 제공자입니다."),
    LOGIN_USER_NOT_FOUND(HttpStatusCode.valueOf(400), "AUTH_4000", "로그인 정보가 유효하지 않습니다."),
    REFRESH_TOKEN_INVALID(HttpStatusCode.valueOf(400), "AUTH_4001", "Refresh Token이 유효하지 않습니다. 재로그인 필요"),

    /**
     * 401 UNAUTHORIZED
     */
    UNAUTHORIZED(HttpStatusCode.valueOf(401), "COMMON_4010", "Auth failed"),
    JWT_NOT_VALID(HttpStatusCode.valueOf(401), "AUTH_4010", "Access Token이 유효하지 않습니다. 재 로그인 필요"),
    REFRESH_NOT_VALID(HttpStatusCode.valueOf(401), "AUTH_4011", "Refresh Token이 유효하지 않습니다, 재 로그인 필요"),
    TOKEN_REFRESH_REQUIRED(HttpStatusCode.valueOf(401), "AUTH_4012", "Access Token 만료, 재발급 필요"),

    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatusCode.valueOf(403), "COMMON_403", "Access Denied"),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND(HttpStatusCode.valueOf(404), "COMMON_4040", "Resource not found"),
    TOKEN_REFRESH_USER_NOT_FOUND(HttpStatusCode.valueOf(404), "AUTH_4040", "Fatal Error: 토큰 갱신요청 유저가 존재하지 않습니다"),
    EMAIL_USER_NOT_FOUND(HttpStatusCode.valueOf(404), "AUTH_4040", "Fatal Error: 해당 이메일의 유저가 존재하지 않습니다"),

    /**
     * 409 CONFLICT
     */
    CONFLICT(HttpStatusCode.valueOf(409), "COMMON_4090", "Requested resource conflict with server resource"),
    SIGN_UP_USER_ALREADY_EXISTS(HttpStatusCode.valueOf(409), "AUTH_4090", "이미 가입된 사용자입니다."),

    /**
     * 500 INTERNAL_SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatusCode.valueOf(500), "COMMON_5000", "Internal server error"),
    OAUTH_INTERNAL_ERROR(HttpStatusCode.valueOf(500), "OAUTH_5000", "Oauth2 Internal server error");

    private final HttpStatusCode status;
    private final String code;
    private final String reason;
}
