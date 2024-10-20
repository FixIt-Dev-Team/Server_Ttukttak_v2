package com.backend.ttukttak_v2.core.auth.application.service;

import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest;
import com.backend.ttukttak_v2.data.mysql.entity.Policy;
import com.backend.ttukttak_v2.data.mysql.entity.User;

import java.util.List;

public interface PolicyService {
    List<Policy> getPolicies();

    void approvePolicies(List<AuthRequest.PolicyApproveReqDto> request, User user);
}
