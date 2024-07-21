package com.backend.ttukttak_v2.core.oauth2.info;

import com.backend.ttukttak_v2.data.mysql.enums.AccountType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public abstract class OAuth2UserInfo {
    private AccountType accountType;
    private String email;
    private String userName;
}
