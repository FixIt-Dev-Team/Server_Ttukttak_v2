package com.backend.ttukttak_v2.base.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 400 BAD_REQUEST
     * */
    BAD_REQUEST(HttpStatusCode.valueOf(400), "COMMON_4000", "Bad request"),

    /**
     * 401 UNAUTHORIZED
     * */
    UNAUTHORIZED(HttpStatusCode.valueOf(401), "COMMON_4010", "Auth failed"),

    /**
     * 404 NOT_FOUND
     * */
    NOT_FOUND(HttpStatusCode.valueOf(404), "COMMON_4040", "Resource not found"),

    /**
     * 409 CONFLICT
     */
    CONFLICT(HttpStatusCode.valueOf(409), "COMMON_4090", "Requested resource conflict with server resource"),

    /**
     * 500 INTERNAL_SERVER_ERROR
     * */
    INTERNAL_SERVER_ERROR(HttpStatusCode.valueOf(500), "COMMON_5000", "Internal server error");
    ;
    private final HttpStatusCode status;
    private final String code;
    private final String reason;
}
