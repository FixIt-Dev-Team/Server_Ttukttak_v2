package com.backend.ttukttak_v2.data.redis.repository;

import com.backend.ttukttak_v2.data.redis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<RefreshToken, Long> {
}
