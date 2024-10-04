package com.backend.ttukttak_v2.data.mysql.entity;

import com.backend.ttukttak_v2.data.mysql.enums.AccountType;
import com.backend.ttukttak_v2.data.mysql.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Slf4j
@Getter
@Builder
@Entity(name = "TB_USER")
@Table(name = "TB_USER")
@AllArgsConstructor
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

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountType", nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "roleType", nullable = false)
    private RoleType roleType;

    @Column(name = "authenticated", nullable = false)
    @ColumnDefault("false")
    private Boolean authenticated;

    @Lob
    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "status")
    @ColumnDefault("true")
    private Boolean status;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateUserPasswd(String newPasswd) {
        this.password = newPasswd;
    }

    public void updateNickName(String newNickName) {
        this.nickName = newNickName;
    }
}
