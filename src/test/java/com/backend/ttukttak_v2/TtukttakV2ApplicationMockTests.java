package com.backend.ttukttak_v2;

import com.backend.ttukttak_v2.core.health.TestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class,MockitoExtension.class})
class TtukttakV2ApplicationMockTests {

    private MockMvc mockMvc;

    @InjectMocks
    private TestController testController;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).apply(documentationConfiguration(restDocumentation)).build();

    }

    @Test
    @DisplayName("TestController Test Example")
    void contextLoads() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/test/mvc"));

        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value("{\"test\":\"clear\"}"))
                .andDo(document("carts-create", resource("Create a cart")))
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());

    }

}
