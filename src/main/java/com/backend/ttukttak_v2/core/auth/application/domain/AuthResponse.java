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
    public static class PasswdResDto {
        private Boolean EmailClear;
    }

    @Getter
    @AllArgsConstructor
    public static class PasswdResetResDto {
        private Boolean Status;
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateAdditonalInfoResDto {
        private Boolean Status;
    }
}
