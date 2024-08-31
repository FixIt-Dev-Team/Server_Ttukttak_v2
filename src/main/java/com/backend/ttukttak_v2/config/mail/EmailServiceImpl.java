package com.backend.ttukttak_v2.config.mail;

import org.springframework.stereotype.Service;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;

import lombok.RequiredArgsConstructor;
import java.security.SecureRandom;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import static jakarta.mail.Message.RecipientType.TO;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private static final SecureRandom random = new SecureRandom();

    @Value("${spring.mail.username}")
    private String myEmail;

    private String ePw;

    // 메일의 내용을 작성하는 클래스
    private MimeMessage createMessage(String email) throws Exception {
        ePw = createKey();

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
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress(myEmail, "뚝딱")); // 보내는 사람

        return message;
    }

    // 이메일로 발송할 인증코드를 생성하는 메서드 => keep In V2
    private static String createKey() {
        StringBuilder key = new StringBuilder();
        random.setSeed(System.currentTimeMillis());

        // 숫자로 구성된 8자리 인증 코드 생성
        for (int i = 0; i < 8; i++) {
            key.append(random.nextInt(10));
        }

        return key.toString();
    }

    // 이메일을 발송하는 메서드
    @Override
    public Boolean sendSimpleMessage(String to) throws BaseException {
        try {
            MimeMessage message = createMessage(to);

            emailSender.send(message);
            return true;

        } catch (Exception e) {
            BaseException.of(ErrorCode.EMAIL_USER_NOT_FOUND);
            return false;
        }
    }

    /**
     * 비밀번호 수정 이메일 전송 메서드
     * */
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
        }
        catch (Exception exception){
            BaseException.of(ErrorCode.EMAIL_USER_NOT_FOUND);
            return false;
        }

    }
}