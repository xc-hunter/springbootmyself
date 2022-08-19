package com.xc.datajpa.entity;
//定义接口投影，用于获取jpa派生方法返回结果的部分属性
//可递归、嵌套投影
//查询执行引擎在运行时为每个返回的元素创建该接口的代理实例，
// 且将对外公开方法的调用转发到目标对象
public interface PartAttr {
    String getUserName();
    String getPassword();
    //第一种
    default String getFulResult(){
        return getUserName()+getPassword();
    }
    /**
     * 第二种:使用@Value结合SpEL表达式语言进行数据处理
     * @Value("#{target.firstname + ' ' + target.lastname}")
     * Srtring getFulResult();
     */
}
