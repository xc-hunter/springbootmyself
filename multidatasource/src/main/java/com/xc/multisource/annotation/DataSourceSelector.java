package com.xc.multisource.annotation;

import com.xc.multisource.constant.ConstHolder;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceSelector {

    String value() default ConstHolder.DEFAULT;
}
