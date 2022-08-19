package com.xc.secret.en;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//数据预先加密
@Component
public class SecurityDataSupport {

    @Autowired
    private StringEncryptor stringEncryptor;
}

