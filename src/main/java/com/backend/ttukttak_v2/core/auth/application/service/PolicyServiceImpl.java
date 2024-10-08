package com.backend.ttukttak_v2.core.auth.application.service;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.data.mysql.entity.Policy;
import com.backend.ttukttak_v2.data.mysql.entity.PolicyApprove;
import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.repository.PolicyApproveRepository;
import com.backend.ttukttak_v2.data.mysql.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.PolicyApproveReqDto;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyApproveRepository policyApproveRepository;

    @Override
    public List<Policy> getPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public void approvePolicies(List<PolicyApproveReqDto> request, User user) {
        List<Policy> policies = policyRepository.findAll();

        List<PolicyApprove> policyApproves = new ArrayList<>();

        if (policies.size() != request.size()) {
            throw new BaseException(ErrorCode.INVALID_POLICY_APPROVE_REQUEST);
        }

        for (int i = 0; i < policies.size(); i++) {
            PolicyApproveReqDto dto = request.get(i);

            policyApproves.add(
                    PolicyApprove.ofApprove(
                            dto,
                            policies.stream().filter(
                                    policy -> policy.getPolicyIdx().equals(dto.getPolicyIdx())
                            ).findFirst().get(),
                            user
                    )
            );
        }

        policyApproveRepository.saveAll(policyApproves);
    }
}
