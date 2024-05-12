package com.backend.ttukttak_v2.data.mysql.enums;

import lombok.Getter;

@Getter
public enum PostStatus {
    ACTIVE, // 활성
    PENDING, // 수정
    INACTIVE // 삭제
}
