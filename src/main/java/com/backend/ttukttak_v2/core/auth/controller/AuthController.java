package com.backend.ttukttak_v2.core.auth.controller;

import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.core.auth.application.AuthFacade;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.LoginReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthFacade authFacade;

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    private ResponseEntity<BaseResponse<LoginResDto>> login(@RequestBody LoginReqDto request) {
        return BaseResponse.onSuccess(authFacade.login(request));
    }

    @GetMapping("/oauth/token")
    private ResponseEntity<BaseResponse<LoginResDto>> changeOauthToToken(@RequestParam("email") String email) {
        return BaseResponse.onSuccess(authFacade.changeOauthToToken(email));
    }

    /**
     * Token 갱신 API
     */
    @PostMapping("/token/refresh")
    private ResponseEntity<Void> refreshToken(@RequestBody AuthRequest.RefreshToken req) {

        HttpHeaders headers = authFacade.refreshToken(req.getRefreshToken());
        return BaseResponse.onAuth(headers);
    }

}
