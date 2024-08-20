package com.backend.ttukttak_v2.data.mysql.repository;

import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndAccountType(String email, AccountType accountType);

    boolean existsByRefreshTokenAndUserIdx(String refreshToken, Long userIdx);

    Optional<User> findByEmailAndPassword(String email, String password);
}
