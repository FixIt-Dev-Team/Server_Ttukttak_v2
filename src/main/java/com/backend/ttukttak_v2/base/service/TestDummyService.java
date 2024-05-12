package com.backend.ttukttak_v2.base.service;


import com.backend.ttukttak_v2.base.dto.DummyReqDto;
import com.backend.ttukttak_v2.base.dto.DummyResDto;
import com.backend.ttukttak_v2.base.util.DummyAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestDummyService {

    private DummyAction action;

    @Autowired
    public TestDummyService(DummyAction action) {
        this.action = action;
    }

    public DummyResDto dummyTest(DummyReqDto input) {

        return action.dummyTest(input);

    }

}
