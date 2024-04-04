package com.backend.ttukttak_v2.base;

import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.base.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * BaseResponse: 모든 응답이 거치는 클래스
 *
 * @author destiny3912
 * @since 2024-02-24
 * */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class BaseResponse<T> {
    private Boolean isSuccess;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 요청 성공 시 사용할 정적 팩터리 메서드
     *
     * @author destiny3912
     * @since 2024-02-24
     * */
    public static <T> ResponseEntity<BaseResponse<Object>> onSuccess(T data) {
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .code(SuccessCode.COMMON_2000)
                        .data(data)
                        .build()

        );
    }

    /**
     * POST 요청 성공 시 사용할 정적 팩터리 메서드
     *
     * @author destiny3912
     * @since 2024-02-24
     * */
    public static <T> ResponseEntity<BaseResponse<Object>> onCreate(T data) {
        return ResponseEntity.status(SuccessCode.COMMON_2010.getStatus()).body(
                BaseResponse.builder()
                        .code(SuccessCode.COMMON_2010)
                        .data(data)
                        .build()
        );
    }

    /**
     * 회원가입과 로그인 시 사용할 정적팩터리 메서드
     *
     * @author destiny3912
     * @since 2024-02-24
     * */
    public static ResponseEntity<BaseResponse<Object>> onAuth(HttpHeaders headers) {
        return ResponseEntity.noContent().headers(headers).build();
    }

    /**
     * errorData가 있는 Exception 처리 시 사용할 정적팩터리 메서드
     *
     * @author destiny3912
     * @since 2024-02-24
     * */
    public static <T> ResponseEntity<BaseResponse<Object>> onFailure(ErrorCode errorCode, T errorData) {
        return ResponseEntity.status(errorCode.getStatus()).body(
                BaseResponse.errorBuilder()
                        .errorCode(errorCode)
                        .message(errorCode.getReason())
                        .errorData(errorData)
                        .errorBuild()
        );
    }

    /**
     * errorData가 없는 Exception 처리 시 사용할 정적팩터리 메서드
     *
     * @author destiny3912
     * @since 2024-02-24
     * */
    public static <T> ResponseEntity<BaseResponse<Object>> onFailure(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus()).body(
                BaseResponse.errorBuilder()
                        .errorCode(errorCode)
                        .message(errorCode.getReason())
                        .errorBuild()
        );
    }

    @Builder
    private BaseResponse(SuccessCode code, T data) {
        this.isSuccess = true;
        this.code = code.name();
        this.message = code.getMessage();
        this.data = data;
    }

    @Builder(builderMethodName = "errorBuilder", buildMethodName = "errorBuild")
    private BaseResponse(ErrorCode errorCode, String message, T errorData) {
        this.isSuccess = false;
        this.code = errorCode.getCode();
        this.message = message;
        this.data = errorData;
    }
}
