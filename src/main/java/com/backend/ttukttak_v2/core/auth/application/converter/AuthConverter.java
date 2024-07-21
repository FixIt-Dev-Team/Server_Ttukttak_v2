package com.backend.ttukttak_v2.core.auth.application.converter;


import com.backend.ttukttak_v2.core.auth.application.info.TokenInfo;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;

/**
 * Request DTO to Entity Converter <br/>
 * Response DTO Converter In Business Logic
 */
public class AuthConverter {

    public static LoginResDto toLoginResponse(TokenInfo tokenInfo) {
        return LoginResDto.of(tokenInfo);
    }
}

