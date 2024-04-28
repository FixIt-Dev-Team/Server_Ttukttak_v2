package com.backend.ttukttak_v2.advice;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.util.SlackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final SlackService slackService;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException exception, HttpServletRequest request) {
        log.error("BaseException Thrown: {} on {}", exception.getErrorCode().toString(), request.getRequestURI());
        slackService.sendErrorLog(exception, request);

        return BaseResponse.onFailure(exception.getErrorCode(), request.getRequestURI());
    }

}
