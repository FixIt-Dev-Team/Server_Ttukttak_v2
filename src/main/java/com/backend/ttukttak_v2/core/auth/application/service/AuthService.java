package com.backend.ttukttak_v2.core.auth.application.service;

import com.backend.ttukttak_v2.data.mysql.entity.User;
import com.backend.ttukttak_v2.data.mysql.enums.AccountType;

public interface AuthService {
    User getUserOnLogin(String email, String password);

    User findUserForPW(String email, AccountType userType);

    boolean checkRefreshToken(String refreshToken, Long userIdx);

    void updateRefreshToken(String refreshToken, Long userIdx);

    boolean updateUserPasswd(long userIdx, String newPasswd);

    boolean updateAdditionalInfo(long userIdx, String newUserName);

}
