package com.backend.ttukttak_v2.util;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Slf4j
@Service
public class SlackServiceImpl implements SlackService {

    @Value("${slack.webhook.notification.error}")
    private String slackErrorNotificationWebhook;

    @Override
    public void sendErrorLog(Exception e, HttpServletRequest request) {
        try {
            Slack.getInstance()
                    .send(slackErrorNotificationWebhook, payload(payloadBuilder ->
                            payloadBuilder
                                    .attachments(List.of(generateSlackAttachment(e, request)))
                    ));

        } catch (Exception ex) {
            log.error("Slack 전송 실패", ex);
        }
    }

    /**
     * Slack Attachment 생성 (에러)
     *
     * @param e       Exception
     * @param request HttpServletRequest
     * @return Attachment
     */
    private Attachment generateSlackAttachment(Exception e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        String xffHeader = request.getHeader("X-FORWARDED-FOR"); // Client IP (프록시 서버나 로드밸런서를 통해 요청이 왔을 경우)

        // Slack Attachment 생성
        return Attachment.builder()
                .color("ff0000") // 빨간색
                .title(requestTime + " 에러 발생!")
                .fields(List.of(
                                generateSlackField("Request IP", xffHeader == null ? request.getRemoteAddr() : xffHeader),
                                generateSlackField("Request URL", request.getRequestURL() + " " + request.getMethod()),
                                generateSlackField("Error Message", e.getMessage())
                        )
                )
                .build();
    }

    /**
     * Slack Field 생성
     *
     * @param title Field 제목
     * @param value Field 값
     * @return Field
     */
    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}