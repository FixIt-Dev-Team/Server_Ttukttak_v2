package com.backend.ttukttak_v2.framework.email;

import com.backend.ttukttak_v2.base.BaseException;

public interface EmailService {
    Boolean sendEmailAuth(String to, String code) throws BaseException;

    Boolean sendPasswordModify(String to, String addr) throws BaseException;

}
