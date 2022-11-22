package com.xc.multisource.config;

import com.xc.multisource.util.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

// 关键点 AbstractRoutingDataSource
// 路由获取数据源
@Slf4j
public class MultiDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        String key= DynamicDataSourceHolder.getDataSource();
        log.info("Current Thread DataSource key **{}**",key);
        return key;
    }
}
