package com.backend.ttukttak_v2.config.mail;

import com.backend.ttukttak_v2.base.BaseException;

public interface EmailService {
    
    Boolean sendSimpleMessage(String to) throws BaseException;

    Boolean sendPasswordModify(String to, String addr) throws BaseException;

}
