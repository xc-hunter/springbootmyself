package com.xc.secret.prodata;

import java.lang.annotation.*;

import static com.xc.secret.prodata.EncryptConstant.ENCRYPT;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptMethod {

    String type() default ENCRYPT;
}
