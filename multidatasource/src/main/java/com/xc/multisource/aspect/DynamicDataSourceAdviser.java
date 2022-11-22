package com.xc.multisource.aspect;

import com.xc.multisource.annotation.DataSourceSelector;
import com.xc.multisource.util.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

// 切面，用于获取被数据源注解指定的方法，拿到其注解中的属性值，设置数据源的key
@Aspect
@Order(100)
public class DynamicDataSourceAdviser {

    // 定义切面
    @Pointcut("@annotation(com.xc.multisource.annotation.DataSourceSelector)")
    public void pointcut(){};

    // 环绕通知
    // 用于获取注解,设置数据源key
    @Around("pointcut()")
    public Object chooseDataSourceForThread(ProceedingJoinPoint point)throws Throwable{
        try{
            // 获取方法签名
            MethodSignature methodSignature=(MethodSignature) point.getSignature();
            // 获取被代理的方法
            Method targetMethod=methodSignature.getMethod();
            // 获取注解
            DataSourceSelector dataSourceSelector=targetMethod.getAnnotation(DataSourceSelector.class);
            // 设置ThreadLocal
            if(!ObjectUtils.isEmpty(dataSourceSelector)){
                DynamicDataSourceHolder.setDataSource(dataSourceSelector.value());
            }
            return point.proceed();
        }finally {
            // 方法执行完毕 清空ThreadLocal,避免内存泄漏
            DynamicDataSourceHolder.remove();
        }
    }
}
