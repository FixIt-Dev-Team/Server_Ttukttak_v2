package com.backend.ttukttak_v2.config.auth.handler;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class OAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error(exception.getMessage() + ": " + request.getRequestURI() + Arrays.toString(exception.getStackTrace()));
        throw BaseException.of(ErrorCode.OAUTH_INTERNAL_ERROR);
    }
}
