package com.backend.ttukttak_v2.data.entity;

import com.backend.ttukttak_v2.data.enums.AccountType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Slf4j
@Getter
@Entity(name = "TB_USER")
@Table(name = "TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx")
    private Long userIdx;

    @Column(name = "email", nullable = false) // length 255
    private String email;

    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickName", nullable = false, length = 50)
    private String nickName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountType", nullable = false)
    private AccountType accountType;

    @Column(name = "authenticated", nullable = false)
    @ColumnDefault("false")
    private Boolean authenticated;

    @Lob
    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "status")
    @ColumnDefault("true")
    private Boolean status;
}
