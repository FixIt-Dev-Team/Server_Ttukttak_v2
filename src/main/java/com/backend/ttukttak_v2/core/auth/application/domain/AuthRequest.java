package com.backend.ttukttak_v2.core.auth.application.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SignUpReqDto {
        @Email
        private String email;
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[1-9]).{8,20}$")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginReqDto {
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RefreshToken {

        private String refreshToken;
    }
}
