package com.backend.ttukttak_v2.core.auth.application.converter;


import com.backend.ttukttak_v2.core.auth.application.info.TokenInfo;
import com.backend.ttukttak_v2.data.mysql.entity.Policy;

import java.util.List;
import java.util.stream.Collectors;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.GetPolicyDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;

/**
 * Request DTO to Entity Converter <br/>
 * Response DTO Converter In Business Logic
 */
public class AuthConverter {

    // Convert TokenInfo to LoginResDto
    public static LoginResDto toLoginResponse(TokenInfo tokenInfo) {
        return LoginResDto.of(tokenInfo);
    }

    // Convert List<Policy> to List<GetPolicyDto>
    public static List<GetPolicyDto> toPolicyResponse(List<Policy> policies) {
        return policies.stream()
                .map(policy -> GetPolicyDto.builder()
                        .policyIdx(policy.getPolicyIdx())
                        .policyName(policy.getName())
                        .policyDesc(policy.getDescription())
                        .isRequired(policy.getIsRequired())
                        .build())
                .collect(Collectors.toList());
    }
}

