package com.xc.sensitive.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author xc
 */
// 包含方法实现的枚举 可以简化 switch case之后的函数业务处理
public enum DesensitizationTypeEnum {
        // 自定义
        MY_RULE {
            @Override
            public String desensitize(String str, Integer startInclude, Integer endExclude) {
                return StrUtil.hide(str, startInclude, endExclude);
            }
        },
        // 用户id
        USER_ID {
            @Override
            public String desensitize(String str, Integer startInclude, Integer endExclude) {
                return String.valueOf(DesensitizedUtil.userId());
            }
        },
        MOBILE_PHONE {
            @Override
            public String desensitize(String str, Integer startInclude, Integer endExclude) {
                return String.valueOf(DesensitizedUtil.mobilePhone(str));
            }
        },
        EMAIL {
            @Override
            public String desensitize(String str, Integer startInclude, Integer endExclude) {
                return String.valueOf(DesensitizedUtil.email(str));
            }
        };
        // 省略其他枚举字段
        // ...
        public abstract String desensitize(String str, Integer startInclude, Integer endExclude);
}
