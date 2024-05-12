package com.backend.ttukttak_v2.data.mysql.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {

    TTUKTTAK("ttukttak"),
    GOOGLE("google");

    private final String type;
}
