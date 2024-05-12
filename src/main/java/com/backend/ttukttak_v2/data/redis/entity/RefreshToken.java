package com.backend.ttukttak_v2.data.redis.entity;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "REFRESH_", timeToLive = 1209600)
public class RefreshToken {

    @Id
    private Long id;

    private String ip;
    
    @Indexed
    private String refreshToken;
}
