package com.backend.ttukttak_v2.data.mysql.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.PolicyApproveReqDto;

@Slf4j
@Getter
@Entity(name = "TB_POLICY_APPROVE")
@Table(name = "TB_POLICY_APPROVE")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyApprove extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approveIdx")
    private Long approveIdx;

    @ManyToOne
    @JoinColumn(referencedColumnName = "policyIdx", name = "policyIdx", nullable = false)
    private Policy policy;

    @ManyToOne
    @JoinColumn(referencedColumnName = "userIdx", name = "userIdx", nullable = false)
    private User user;

    @Column(name = "approved", nullable = false)
    private Boolean approved;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public static PolicyApprove ofApprove(PolicyApproveReqDto dto, Policy policy, User user) {
        return PolicyApprove.builder()
                .policy(policy)
                .user(user)
                .approved(dto.getIsApprove())
                .status(true)
                .build();

    }
}
