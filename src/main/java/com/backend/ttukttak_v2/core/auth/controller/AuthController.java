package com.backend.ttukttak_v2.core.auth.controller;

import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.core.auth.application.AuthFacade;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest;
import com.backend.ttukttak_v2.framework.jwt.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.*;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthFacade authFacade;
    private final JwtService jwtService;

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

    /**
     * OAuth 로그인 시 Token 발급 API
     */
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

        return BaseResponse.onSuccess(authFacade.passwdResetReq(request.getEmail(), request.getAccountType()));
    }

    /**
     * ResetPasswd (updateDB)
     */
    @PostMapping("/passwd/reset")
    public ResponseEntity<BaseResponse<PasswdResetResDto>> resetPassword(@RequestBody PasswdResetReqDto request) {

        return BaseResponse.onSuccess(authFacade.setPasswdReq(request.getPasswdRestToken(), request.getNewPasswd()));
    }

    /**
     * 약관 조회 API
     */
    @GetMapping("/agree")
    public ResponseEntity<BaseResponse<List<GetPolicyDto>>> getPolicy() {
        return BaseResponse.onSuccess(authFacade.getPolicy());
    }

    /**
     * 약관 동의 API
     */
    @PostMapping("/agree")
    public ResponseEntity<BaseResponse<Boolean>> agreePolicy(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken, @RequestBody List<PolicyApproveReqDto> request) {
        Long userIdx = jwtService.validateAccess(accessToken);
        authFacade.agreePolicy(request, userIdx);
        return BaseResponse.onSuccess(true);
    }

}
