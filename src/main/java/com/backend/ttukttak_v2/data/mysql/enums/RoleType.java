package com.backend.ttukttak_v2.data.mysql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    USER("일반 사용자"),
    ADMIN("관리자");

    private final String description;
}
