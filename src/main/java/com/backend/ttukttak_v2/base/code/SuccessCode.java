package com.backend.ttukttak_v2.base.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    COMMON_2000(HttpStatusCode.valueOf(200), "Request success"),
    COMMON_2010(HttpStatusCode.valueOf(201), "Request created"),
    COMMON_2040(HttpStatusCode.valueOf(204), "Data in header");

    private final HttpStatusCode status;
    private final String message;

}
