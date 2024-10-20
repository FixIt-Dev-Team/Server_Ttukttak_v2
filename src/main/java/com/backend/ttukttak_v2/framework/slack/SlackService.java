package com.backend.ttukttak_v2.framework.slack;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Slack 서비스를 사용하는 기능을 제공하는 서비스 클래스
 */
public interface SlackService {

    /**
     * Error 발생 시 Slack으로 에러 로그를 전송하는 메서드
     *
     * @param e       Exception
     * @param request HttpServletRequest
     */
    void sendErrorLog(Exception e, HttpServletRequest request);
}