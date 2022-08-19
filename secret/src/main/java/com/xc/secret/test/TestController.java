package com.xc.secret.test;

import com.alibaba.fastjson.JSON;
import com.xc.secret.prodata.EncryptField;
import com.xc.secret.prodata.EncryptMethod;
import lombok.Data;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private StringEncryptor stringEncryptor;

    public void encrypt(String content) {
        String encryptStr = stringEncryptor.encrypt("xiaofu");
        System.out.println("加密后的内容：" + encryptStr);
    }

    //使用加密注解
    @EncryptMethod
    @PostMapping(value = "test")
    @ResponseBody
    public Object testEncrypt(@RequestBody UserVo user, @EncryptField String name) {
        //方法执行完成之后，返回值，值中数据为加密值
        //AOP处使用的Around，decrypt会进行解密操作
        return insertUser(user, name);
    }

    private UserVo insertUser(UserVo user, String name) {
        //持久化操作，存储的是加密数据
        System.out.println("加密后的数据：user" + JSON.toJSONString(user));
        return user;
    }

    @Data
    public class UserVo implements Serializable {

        private Long userId;

        @EncryptField
        private String mobile;

        @EncryptField
        private String address;

        private String age;
    }

}
