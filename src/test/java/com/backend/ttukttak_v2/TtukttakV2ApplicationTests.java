package com.backend.ttukttak_v2;

import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.base.dto.DummyReqDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TtukttakV2ApplicationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private MockMvc mockMvc;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    @DisplayName("TestController Test Example")
    void contextLoads() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<DummyReqDto> entity =
                new HttpEntity<>(DummyReqDto.builder().input(10).build(),headers);

        ResponseEntity<BaseResponse> dummyResult
                = restTemplate.exchange("http://localhost:"+randomServerPort+"/test/mvc",
                HttpMethod.GET,entity, BaseResponse.class);

        Assertions.assertEquals(
                BaseResponse.onSuccess("{\"test\":\"clear\"}").getBody().getData(),
                dummyResult.getBody().getData()
        );

        System.out.println("mvcResult :: " + dummyResult.toString());

    }

}
