package com.backend.ttukttak_v2.core.oauth2.service;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.core.oauth2.OAuthAttributes;
import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.enums.AccountType;
import com.backend.ttukttak_v2.data.mysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * OAuth 관련 UserService </br>
 * OAuthUserService를 구현함
 */
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultUserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = defaultUserService.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        AccountType type = AccountType
                .find(registrationId)
                .orElseThrow(() -> BaseException.of(ErrorCode.OAUTH_BAD_PROVIDER));

        OAuthAttributes attributes = OAuthAttributes.of(
                type,
                userNameAttributeName,
                oAuth2User.getAttributes()
        );

        User user = getUser(attributes, type);

        // TODO 요구사항에 따라 커스텀 유저 구현 필요시 구현
        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRoleType().name())),
                attributes.getAttributes(),
                attributes.getKey()
        );
    }

    /**
     * 로그인 요청한 유저가 존재하면 찾아오고 없다면 회원가입 처리 시키는 메서드
     */
    private User getUser(OAuthAttributes attributes, AccountType type) {

        return userRepository
                .findUserByEmailAndAccountType(attributes.getUserInfo().getEmail(), type)
                .orElse(
                        saveUser(attributes)
                );
    }

    /**
     * 유저 저장하는 메서드
     */
    private User saveUser(OAuthAttributes attributes) {
        User user = attributes.toUser(attributes.getUserInfo());
        return userRepository.save(user);
    }
}
