package com.backend.ttukttak_v2.core.auth.application.service;

import com.backend.ttukttak_v2.data.mysql.entity.User;

public interface AuthService {
    User getUserOnLogin(String email, String password);

    boolean checkRefreshToken(String refreshToken, Long userIdx);

    void updateRefreshToken(String refreshToken, Long userIdx);

    void signUp(String email, String password);

    String generateVerifyCode();
}
