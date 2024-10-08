package com.backend.ttukttak_v2.framework.email;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static jakarta.mail.Message.RecipientType.TO;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private static final SecureRandom random = new SecureRandom();

    @Value("${spring.mail.username}")
    private String myEmail;

    // 메일의 내용을 작성하는 클래스
    private MimeMessage createEmailAuthMessage(String email, String code) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(TO, email); // 보내는 대상
        message.setSubject("[뚝딱] 회원 가입"); // 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요. 뚝딱입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요.<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += code + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress(myEmail, "뚝딱")); // 보내는 사람

        return message;
    }

    // 이메일을 발송하는 메서드
    @Override
    public Boolean sendEmailAuth(String to, String code) throws BaseException {
        try {
            MimeMessage message = createEmailAuthMessage(to, code);

            emailSender.send(message);
            return true;

        } catch (Exception e) {
            throw BaseException.of(ErrorCode.EMAIL_USER_NOT_FOUND);
        }
    }

    /**
     * 비밀번호 수정 이메일 전송 메서드
     */
    @Override
    public Boolean sendPasswordModify(String to, String TemporalKey) throws BaseException {

        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.addRecipients(TO, to); // 보내는 대상
            message.setSubject("[뚝딱] 비밀번호 찾기"); // 제목

            message.setText(TemporalKey);
            message.setFrom(new InternetAddress(myEmail, "뚝딱")); // 보내는 사람

            emailSender.send(message);

            return true;
        } catch (Exception exception) {
            BaseException.of(ErrorCode.EMAIL_USER_NOT_FOUND);
            return false;
        }

    }
}