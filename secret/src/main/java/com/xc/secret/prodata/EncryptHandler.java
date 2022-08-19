package com.xc.secret.prodata;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.xc.secret.prodata.EncryptConstant.DECRYPT;
import static com.xc.secret.prodata.EncryptConstant.ENCRYPT;

@Slf4j
@Aspect
@Component
public class EncryptHandler {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Pointcut("@annotation(com.xc.secret.prodata.EncryptMethod)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        /**
         * 加密
         */
        encrypt(joinPoint);


        /**
         * 解密
         */
        Object decrypt = decrypt(joinPoint);
        //返回解密之后的数据
        return decrypt;
    }

    public void encrypt(ProceedingJoinPoint joinPoint) {

        try {
            Object[] objects = joinPoint.getArgs();
            if (objects.length != 0) {
                for (Object o : objects) {
                    if (o instanceof String) {
                        encryptValue(o);
                    } else {
                        handler(o, ENCRYPT);
                    }
                    //TODO 其余类型自己看实际情况加
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object decrypt(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            //执行目标方法，进行加密值的逻辑处理
            Object obj = joinPoint.proceed();
            //解密
            if (obj != null) {
                if (obj instanceof String) {
                    decryptValue(obj);
                } else {
                    result = handler(obj, DECRYPT);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object handler(Object obj, String type) throws IllegalAccessException {

        if (Objects.isNull(obj)) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            //检查注解
            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
            if (hasSecureField) {
                //修改访问
                field.setAccessible(true);
                String realValue = (String) field.get(obj);
                String value;
                if (DECRYPT.equals(type)) {
                    value = stringEncryptor.decrypt(realValue);
                } else {
                    value = stringEncryptor.encrypt(realValue);
                }
                //注解的值修改
                field.set(obj, value);
            }
        }
        return obj;
    }

    public String encryptValue(Object realValue) {
        String value = null;
        try {
            value = stringEncryptor.encrypt(String.valueOf(realValue));
        } catch (Exception ex) {
            return value;
        }
        return value;
    }

    public String decryptValue(Object realValue) {
        String value = String.valueOf(realValue);
        try {
            value = stringEncryptor.decrypt(value);
        } catch (Exception ex) {
            return value;
        }
        return value;
    }
}
