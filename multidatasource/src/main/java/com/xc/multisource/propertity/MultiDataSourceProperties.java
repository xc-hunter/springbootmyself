package com.xc.multisource.propertity;

import com.xc.multisource.constant.ConstHolder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

// 存储配置的数据源信息
@Component
@ConfigurationProperties(prefix = ConstHolder.CONFIG_PREFIX)
@Data
public class MultiDataSourceProperties {

    private Map<String, DataSourceProp> dataSourcePropMap;
}
