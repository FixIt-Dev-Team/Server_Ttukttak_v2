package com.backend.ttukttak_v2.core.auth.application;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.config.jwt.JwtService;
import com.backend.ttukttak_v2.config.mail.EmailService;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.LoginReqDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.PasswdResDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.PasswdResetResDto;
import com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.UpdateAdditonalInfoResDto;
import com.backend.ttukttak_v2.core.auth.application.info.TokenInfo;
import com.backend.ttukttak_v2.core.auth.application.service.AuthService;
import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.enums.AccountType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.backend.ttukttak_v2.core.auth.application.converter.AuthConverter.toLoginResponse;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthRequest.LoginReqDto;
import static com.backend.ttukttak_v2.core.auth.application.domain.AuthResponse.LoginResDto;

/**
 * Auth 퍼사드 클래스
 * <p>
 * 각종 서비스에서 데이터를 가져오거나 로직을 수행하고 리턴값을 조립하는 책임을 가지는 클래스
 */
@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final JwtService jwtService;
    private final AuthService authService;
    private final EmailService emailService;

    public LoginResDto login(LoginReqDto request) {
        User user = authService.getUserOnLogin(request.getEmail(), request.getPassword());

        TokenInfo info = TokenInfo.builder()
                .accessToken(jwtService.createAccess(user.getUserIdx()))
                .refreshToken(jwtService.createRefresh(user.getUserIdx()))
                .build();

        user.updateRefreshToken(info.getRefreshToken());

        return toLoginResponse(info);
    }

    public LoginResDto changeOauthToToken(String email) {
        User user = authService.getUserOnLogin(email, email);

        TokenInfo info = TokenInfo.builder()
                .accessToken(jwtService.createAccess(user.getUserIdx()))
                .refreshToken(jwtService.createRefresh(user.getUserIdx()))
                .build();

        user.updateRefreshToken(info.getRefreshToken());

        return toLoginResponse(info);
    }

    public HttpHeaders refreshToken(String refreshToken) {
        Long userIdx = jwtService.validateRefresh(refreshToken);
        if (!authService.checkRefreshToken(refreshToken, userIdx)) {
            throw BaseException.of(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        TokenInfo info = TokenInfo.builder()
                .accessToken(jwtService.createAccess(userIdx))
                .refreshToken(jwtService.createRefresh(userIdx))
                .build();

        authService.updateRefreshToken(info.getRefreshToken(), userIdx);

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();

        header.add("accessToken", info.getAccessToken());
        header.add("refreshToken", info.getRefreshToken());

        return HttpHeaders.readOnlyHttpHeaders(header);
    }

    public PasswdResDto PasswdResetReq(String email , String userType){
        
        AccountType userAccountType = AccountType
                .find(userType)
                .orElseThrow(() -> BaseException.of(ErrorCode.OAUTH_BAD_PROVIDER));

        User user = authService.findUserForPW(email, userAccountType);
        
        Long userIdx = user.getUserIdx();

        String temporal_token = jwtService.createAccess(userIdx);

        if (emailService.sendPasswordModify(user.getEmail(), temporal_token)){
            return new PasswdResDto(true);
        } else {
            return new PasswdResDto(false);
        }

    }

    public PasswdResetResDto SetpasswdReq(String ResetToken , String newPasswd){

        long userIdx = jwtService.validateAccess(ResetToken);

        authService.updateUserPasswd(userIdx, newPasswd);

        return new PasswdResetResDto(true);
    }

    public UpdateAdditonalInfoResDto SetAdditionalInfoReq(long userIdx, String UserName){

        authService.updateAdditionalInfo(userIdx, UserName);

        return new UpdateAdditonalInfoResDto(true);

    }


}
