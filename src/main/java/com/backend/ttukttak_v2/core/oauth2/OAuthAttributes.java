package com.backend.ttukttak_v2.core.oauth2;

import com.backend.ttukttak_v2.base.BaseException;
import com.backend.ttukttak_v2.base.code.ErrorCode;
import com.backend.ttukttak_v2.core.oauth2.info.GoogleOAuth2UserInfo;
import com.backend.ttukttak_v2.core.oauth2.info.OAuth2UserInfo;
import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.enums.AccountType;
import com.backend.ttukttak_v2.data.mysql.enums.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    private String key;
    private OAuth2UserInfo userInfo;
    private Map<String, Object> attributes;

    public User toUser(OAuth2UserInfo userInfo) {
        return User.builder()
                .email(userInfo.getEmail())
                .accountType(userInfo.getAccountType())
                .password(userInfo.getEmail())
                .nickName(userInfo.getUserName())
                .roleType(RoleType.USER)
                .authenticated(false)
                .build();
    }

    /**
     * OAuthAttribute 공통 정적펙터리 메서드 <br/>
     * <p>
     * Provider에 맞추어 각각 커스텀된 OAuthAttributes 펙터리 메서드에 라우팅해준다 <br/>
     * OAuth 추가시 여기에 라우팅 추가할 것
     */
    public static OAuthAttributes of(AccountType type, String userNameAttributeName, Map<String, Object> attributes) {
        if (type.getType().equals(AccountType.GOOGLE.getType())) {
            return ofGoogle(userNameAttributeName, attributes);
        } else {
            throw BaseException.of(ErrorCode.OAUTH_BAD_PROVIDER);
        }
    }

    /**
     * 구글 OAuth 로그인 전용 펙터리 메서드
     */
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .key(userNameAttributeName)
                .userInfo(GoogleOAuth2UserInfo.of(attributes))
                .attributes(attributes)
                .build();
    }
}
