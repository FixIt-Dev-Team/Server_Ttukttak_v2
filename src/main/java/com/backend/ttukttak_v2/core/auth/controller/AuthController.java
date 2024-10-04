package com.backend.ttukttak_v2.core.auth.controller;

import com.backend.ttukttak_v2.base.BaseResponse;
import com.backend.ttukttak_v2.core.auth.application.AuthFacade;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.PasswdReqDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.PasswdResetReqDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.UpdateAdditionalInfoReqDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.PasswdResDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.PasswdResetResDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.UpdateAdditonalInfoResDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.LoginReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    /**
     * SetUserName (updateDB)
     */
    @PostMapping("/additional/info")
    public ResponseEntity<BaseResponse<UpdateAdditonalInfoResDto>> setAdditionalInfo(@RequestAttribute("userIdx") long userIdx, @RequestBody UpdateAdditionalInfoReqDto request) {
        
        return BaseResponse.onSuccess(authFacade.SetAdditionalInfoReq(userIdx,request.getUserName()));
    }
    
}
