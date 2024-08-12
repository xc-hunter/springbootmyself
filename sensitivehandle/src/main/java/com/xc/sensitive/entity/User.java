package com.xc.sensitive.entity;

import com.xc.sensitive.annotation.Desensitization;
import com.xc.sensitive.serialize.DesensitizationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xc
 */
// 测试
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // 演示自定义脱敏
    @Desensitization(type = DesensitizationTypeEnum.MY_RULE, startInclude = 4, endExclude = 7)
    private String userid;

    @Desensitization(type = DesensitizationTypeEnum.MOBILE_PHONE)
    private String phone;

    @Desensitization(type = DesensitizationTypeEnum.EMAIL)
    private String email;
}
