package com.backend.ttukttak_v2.base;

import com.backend.ttukttak_v2.base.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BaseException: 서버 자체 익셉션
 *
 * @Usage BaseException.of({ErrorCode})
 * @author destiny3912
 * @since 2024-02-24
 * */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private final ErrorCode errorCode;

    /**
     * BaseException: 정적 팩토리
     *
     * @author destiny3912
     * @since 2024-02-24
     * */
    public static BaseException of(ErrorCode errorCode) {
        return new BaseException(errorCode);
    }
}
