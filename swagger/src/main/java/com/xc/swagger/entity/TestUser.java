package com.xc.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("测试用户类")
@Data
public class TestUser {

    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    @ApiModelProperty(value = "性别",required = true)
    private String sex;
    @ApiModelProperty(value = "年纪",required = true)
    private Integer age;
    @ApiModelProperty(value = "生日")
    private Date birthday;
}
