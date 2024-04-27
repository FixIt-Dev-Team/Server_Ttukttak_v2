package com.backend.ttukttak_v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TtukttakV2ApplicationMockTests {

    @Mock
    private MockMvc mockMvc;

    @InjectMocks
    private TestController testController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();

    }

    @Test
    @DisplayName("TestController Test Example")
    void contextLoads() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/test/mvc"));

        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value("{\"test\":\"clear\"}"))
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());

    }

}
