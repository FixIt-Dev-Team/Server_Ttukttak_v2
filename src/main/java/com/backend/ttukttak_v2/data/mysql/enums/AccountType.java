package com.backend.ttukttak_v2.data.mysql.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum AccountType {

    TTUKTTAK("ttukttak"),
    GOOGLE("google");

    private final String type;

    public static Optional<AccountType> find(String type) {
        return Arrays.stream(AccountType.values())
                .filter(value -> value.getType().equals(type))
                .findFirst();
    }
}
