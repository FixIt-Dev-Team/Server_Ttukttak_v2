package com.backend.ttukttak_v2.core.auth.application.info;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenInfo {

    private String accessToken;
    private String refreshToken;
}