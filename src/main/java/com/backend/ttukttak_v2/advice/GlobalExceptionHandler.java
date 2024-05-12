package com.backend.ttukttak_v2.advice;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.util.SlackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final SlackService slackService;

    /**
     * 예외를 처리하는 메서드
     *
     * @param exception 발생한 예외
     * @param request   요청 객체
     * @return 응답 객체
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception exception, HttpServletRequest request) {

        // 발생한 예외에 따라 다른 응답을 반환
        if (exception instanceof BaseException baseException) { // BaseException이 발생한 경우
            log.warn("BaseException Thrown: {} on {}", baseException.getErrorCode().toString(), request.getRequestURI());
            return BaseResponse.onFailure(baseException.getErrorCode(), request.getRequestURI());

        } else { // 그 외의 Exception이 발생한 경우 (예상치 못한 Exception이 발생한 경우)
            log.error("Exception Thrown: {} on {}", exception.getMessage(), request.getRequestURI());
            slackService.sendErrorLog(exception, request);
            return BaseResponse.onFailure(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI());
        }
    }

    /**
     * Request Parameter가 잘못된 경우 발생하는 Exception을 처리하는 메서드
     *
     * @param ex      the exception to handle
     * @param headers the headers to use for the response
     * @param status  the status code to use for the response
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object body = BaseResponse.onFailure(ErrorCode.BAD_REQUEST, ex.getMessage());

        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getStatus())
                .body(body);
    }

    /**
     * Path Variable이 잘못된 경우 발생하는 Exception을 처리하는 메서드
     *
     * @param ex      the exception to handle
     * @param headers the headers to use for the response
     * @param status  the status code to use for the response
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object body = BaseResponse.onFailure(ErrorCode.BAD_REQUEST, ex.getMessage());

        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getStatus())
                .body(body);
    }

    /**
     * Validation에 실패한 경우 발생하는 Exception을 처리하는 메서드
     *
     * @param ex      the exception to handle
     * @param headers the headers to use for the response
     * @param status  the status code to use for the response
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // 필드명과 메시지를 조합하여 하나의 문자열로 만들기
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage()) // 필드명과 메시지를 조합
                .reduce("", (acc, cur) -> acc + cur + " / "); // 모든 필드에 대한 메시지를 하나의 문자열로 조합

        Object body = BaseResponse.onFailure(ErrorCode.BAD_REQUEST, message);

        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getStatus())
                .body(body);
    }
}
