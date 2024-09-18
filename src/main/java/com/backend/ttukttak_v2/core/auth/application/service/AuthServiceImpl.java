package com.backend.ttukttak_v2.core.auth.application.service;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.base.util.EncodeUtil;
import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    public User getUserOnLogin(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(() ->
                BaseException.of(ErrorCode.LOGIN_USER_NOT_FOUND));
    }

    @Override
    public boolean checkRefreshToken(String refreshToken, Long userIdx) {
        return userRepository.existsByRefreshTokenAndUserIdx(refreshToken, userIdx);
    }

    @Override
    public void updateRefreshToken(String refreshToken, Long userIdx) {
        User user = userRepository.findById(userIdx).orElseThrow(() ->
                BaseException.of(ErrorCode.TOKEN_REFRESH_USER_NOT_FOUND));

        user.updateRefreshToken(refreshToken);
    }

    @Override
    public void signUp(String email, String password) {
        password = EncodeUtil.encode(password);

        userRepository.findByEmailAndPassword(email, password).ifPresent(user -> {
            throw BaseException.of(ErrorCode.SIGN_UP_USER_ALREADY_EXISTS);
        });

        userRepository.save(User.builder()
                .email(email)
                .password(password)
                .build());
    }

    @Override
    public String generateVerifyCode() {
        return String.valueOf((int) (Math.random() * 1000000));
    }
}
