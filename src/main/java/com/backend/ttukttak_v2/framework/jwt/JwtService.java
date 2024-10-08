package com.backend.ttukttak_v2.framework.jwt;


import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String tokenSecret;

    @Value("${jwt.expire.access}")
    private long accessTokenExpire;

    @Value("${jwt.expire.refresh}")
    private long refreshTokenExpire;

    private SecretKey key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(tokenSecret.getBytes());
    }

    /**
     * Access Token 생성 메서드
     */
    public String createAccess(long userIdx) {

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpire))
                .claim("userIdx", userIdx)
                .encodePayload(true)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Refresh Token 생성 메서드
     */
    public String createRefresh(Long userIdx) {

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .claim("userIdx", userIdx)
                .encodePayload(true)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Access Token 검증 및 userIdx 파싱 메서드
     */
    public long validateAccess(String accessToken) {

        Jwt<Header, Claims> jwtClaims;
        long userIdx;

        try {
            jwtClaims = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuedAt(new Date(System.currentTimeMillis() - accessTokenExpire))
                    .build()
                    .parseUnsecuredClaims(accessToken);

            return (long) jwtClaims.getPayload().get("userIdx");
        } catch (Exception exception) {
            throw BaseException.of(ErrorCode.JWT_NOT_VALID);
        }
    }

    /**
     * Refresh Token 검증 및 userIdx 파싱 메서드
     */
    public long validateRefresh(String refreshToken) {
        Jwt<Header, Claims> jwtClaims;
        long userIdx;

        try {
            jwtClaims = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuedAt(new Date(System.currentTimeMillis() - refreshTokenExpire))
                    .build()
                    .parseUnsecuredClaims(refreshToken);

            return (long) jwtClaims.getPayload().get("userIdx");
        } catch (Exception exception) {
            throw BaseException.of(ErrorCode.REFRESH_NOT_VALID);
        }
    }
}
