package com.backend.ttukttak_v2;

import com.backend.ttukttak_v2.base.dto.DummyReqDto;
import com.backend.ttukttak_v2.base.dto.DummyResDto;
import com.backend.ttukttak_v2.base.util.DummyAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.backend.ttukttak_v2.base.service.TestDummyService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.notNull;

@ExtendWith(MockitoExtension.class)
class TtukttakMockingExampleTest {

    @Mock
    private DummyAction action;

    @InjectMocks
    private TestDummyService dummyService;

    @BeforeEach
    void setUp() {
        System.out.println("[+] setup Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("[+] Test Clean");
    }

    @Test
    void test1() {
        System.out.println("[+] Test start");

        DummyReqDto mockReqDto = DummyReqDto.builder().input(10).build();
        DummyResDto mockResDto = DummyResDto.builder().result(0).build();

        Mockito.when(
                action.dummyTest(
                        (DummyReqDto)notNull()
                )
        ).thenReturn(mockResDto);

        DummyResDto result = dummyService.dummyTest(mockReqDto);

        System.out.println(result.getResult());

        Assertions.assertEquals(result,mockResDto);
    }
}