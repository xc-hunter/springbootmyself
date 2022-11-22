package com.xc.multisource.config;

import com.xc.multisource.util.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

// 关键点 AbstractRoutingDataSource
// 路由获取数据源
// 在获取数据源连接时,getConnection()基于查找key路由到数据库 key通常通过某些线程绑定的事务上下文确定
@Slf4j
public class MultiDataSource extends AbstractRoutingDataSource {

    // 决定当前路由key选择数据源
    @Override
    protected Object determineCurrentLookupKey() {
        String key= DynamicDataSourceHolder.getDataSource();
        log.info("Current Thread DataSource key **{}**",key);
        return key;
    }
}
