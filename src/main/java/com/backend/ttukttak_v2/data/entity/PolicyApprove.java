package com.backend.ttukttak_v2.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "TB_POLICY_APPROVE")
@Table(name = "TB_POLICY_APPROVE")
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
}
