package com.backend.ttukttak_v2.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Slf4j
@Getter
@Entity(name = "TB_POLICY")
@Table(name = "TB_POLICY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policyIdx")
    private Long policyIdx;

    @Column(name = "policyName", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "policyDescription", nullable = false)
    private String description;

    @Column(name = "isRequired", nullable = false)
    private Boolean isRequired;

    @Column(name = "status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;
}
