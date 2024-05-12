package com.backend.ttukttak_v2.data.mysql.repository;

import com.backend.ttukttak_v2.data.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
