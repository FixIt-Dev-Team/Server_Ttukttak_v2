package com.backend.ttukttak_v2.core.auth.application.domain;

import com.backend.ttukttak_v2.core.auth.application.info.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthResponse {


    @Getter
    @AllArgsConstructor
    public static class LoginResDto {
        private TokenInfo tokenInfo;

        public static LoginResDto of(TokenInfo tokenInfo) {
            return new LoginResDto(tokenInfo);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class VerifyEmailResDto {
        private String code;

        public static VerifyEmailResDto of(String code) {
            return new VerifyEmailResDto(code);
        }
    }
}
