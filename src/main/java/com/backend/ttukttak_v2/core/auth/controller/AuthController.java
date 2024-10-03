package com.backend.ttukttak_v2.core.auth.controller;

import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.core.auth.application.AuthFacade;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.PasswdReqDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.PasswdResetReqDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.PasswdResDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.PasswdResetResDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.LoginReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.SignUpReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.VerifyEmailResDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    /**
     * password Find request(reset)
     */
    @PostMapping("/passwd")
    public ResponseEntity<BaseResponse<PasswdResDto>> resetPassword(@RequestBody PasswdReqDto request) {

        return BaseResponse.onSuccess(authFacade.PasswdResetReq(request.getEmail(), request.getAccountType()));
    }

    /**
     * ResetPasswd (updateDB)
     */
    @PostMapping("/passwd/reset")
    public ResponseEntity<BaseResponse<PasswdResetResDto>> resetPassword(@RequestBody PasswdResetReqDto request) {

        return BaseResponse.onSuccess(authFacade.SetpasswdReq(request.getPasswdRestToken(), request.getNewPasswd()));
    }

}
