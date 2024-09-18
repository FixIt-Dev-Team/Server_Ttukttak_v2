package com.backend.ttukttak_v2.core.auth.controller;

import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.core.auth.application.AuthFacade;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.LoginReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.SignUpReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.VerifyEmailResDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthFacade authFacade;

    /**
     * 회원가입 API
     */
    @PostMapping("signup")
    private ResponseEntity<BaseResponse<Void>> signUp(@Valid @RequestBody SignUpReqDto request) {
        authFacade.signUp(request);
        return BaseResponse.onSuccess(null);
    }

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
     * 이메일 인증 API
     */
    @GetMapping("/verify/email")
    private ResponseEntity<BaseResponse<VerifyEmailResDto>> verifyEmail(@Email @RequestParam("email") String email) {
        return BaseResponse.onSuccess(authFacade.verifyEmail(email));
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
