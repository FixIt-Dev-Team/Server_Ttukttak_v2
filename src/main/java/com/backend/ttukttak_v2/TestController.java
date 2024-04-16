package com.backend.ttukttak_v2;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Object>> test() {
        throw BaseException.of(ErrorCode.NOT_FOUND);
    }
}
