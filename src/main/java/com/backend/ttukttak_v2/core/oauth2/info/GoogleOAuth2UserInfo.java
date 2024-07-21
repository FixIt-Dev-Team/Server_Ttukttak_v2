package com.backend.ttukttak_v2.core.oauth2.info;


import com.backend.ttukttak_v2.data.mysql.enums.AccountType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public static GoogleOAuth2UserInfo of(Map<String, Object> attributes) {
        return GoogleOAuth2UserInfo.builder()
                .accountType(AccountType.GOOGLE)
                .userName((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }
}
