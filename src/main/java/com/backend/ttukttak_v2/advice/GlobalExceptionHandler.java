package com.backend.ttukttak_v2.advice;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException exception, HttpServletRequest request) {
        log.debug("BaseException Thrown: {} on {}", exception.getErrorCode().toString(), request.getRequestURI());

        return BaseResponse.onFailure(exception.getErrorCode(), request.getRequestURI());
    }

}
