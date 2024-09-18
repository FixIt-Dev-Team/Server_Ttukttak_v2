package com.backend.ttukttak_v2.util;

public interface EmailService {
    void sendEmail(String targetEmail, String title, String message);
}
