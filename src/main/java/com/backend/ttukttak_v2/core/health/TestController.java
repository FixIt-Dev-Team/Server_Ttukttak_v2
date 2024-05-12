package com.backend.ttukttak_v2.core.health;

import com.backend.ttukttak_v2.base.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Object>> test() {
        return BaseResponse.onSuccess("ok");
    }

    @GetMapping(value = "/mvc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Object>> testMvc() {
        return BaseResponse.onSuccess("{\"test\":\"clear\"}");
    }
}
