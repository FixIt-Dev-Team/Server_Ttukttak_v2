package com.backend.ttukttak_v2.base.util;

import com.backend.ttukttak_v2.base.dto.DummyReqDto;
import com.backend.ttukttak_v2.base.dto.DummyResDto;
import org.springframework.stereotype.Repository;

@Repository
public class DummyAction {

    public DummyResDto dummyTest(DummyReqDto input) {
        int tmp = input.getInput();

        tmp = tmp * 2;

        return DummyResDto.builder().result(tmp).build();

    }

}
