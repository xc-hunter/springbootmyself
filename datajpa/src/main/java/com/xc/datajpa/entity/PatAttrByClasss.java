package com.xc.datajpa.entity;

import lombok.Value;

//定义投影的另一种方法是使用值类型 DTO（数据传输对象），
// 这些 DTO 包含应该检索的字段的属性。
// 这些 DTO 类型可以以与使用投影接口完全相同的方式使用，除了不发生代理并且不可以应用嵌套投影
//通过DTO类的构造函数限制查询结果
@Value
public class PatAttrByClasss {
    String userName,password;

}
