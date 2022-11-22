package com.xc.multisource.config;

import com.xc.multisource.aspect.DynamicDataSourceAdviser;
import com.xc.multisource.constant.ConstHolder;
import com.xc.multisource.propertity.DataSourceProp;
import com.xc.multisource.propertity.MultiDataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class MultiDataSourceConfig {

    public static final String DS_TYPE="dsType";

    @Resource
    private MultiDataSourceProperties multiDataSourceProperties;

    // 全局数据源配置为 路由数据源
    @Bean(ConstHolder.MULTI_DS)
    @Primary
    public DataSource multiDataSource(){
        MultiDataSource multiDataSource=new MultiDataSource();
        Map<Object,Object> dataSourceMap=new HashMap<>(multiDataSourceProperties.getDataSourcePropMap().size());
        Map<String, DataSourceProp> dataSourcePropMap = multiDataSourceProperties.getDataSourcePropMap();
        dataSourcePropMap.forEach((lookupKey,dsProp) -> {
            dataSourceMap.put(lookupKey, createDs(dsProp));
        });
        multiDataSource.setTargetDataSources(dataSourceMap);
        multiDataSource.setDefaultTargetDataSource(dataSourceMap.get(ConstHolder.DEFAULT));
        return multiDataSource;
    }


    // 数据源事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(ConstHolder.MULTI_DS)DataSource multiDataSource){
        DataSourceTransactionManager tx=new DataSourceTransactionManager();
        tx.setDataSource(multiDataSource);
        return tx;
    }

    @Bean
    public DynamicDataSourceAdviser dynamicDataSourceAdviser(){
        return new DynamicDataSourceAdviser();
    }

    // DataSource依据配置的属性进行创建
    private DataSource createDs(DataSourceProp dataSourceProp){
        DataSource dataSource=null;
        try {
            Class<?> dsClass=Class.forName(dataSourceProp.get(DS_TYPE));
            // 检查配置的数据源Class是否可转换为DataSource
            if(DataSource.class.isAssignableFrom(dsClass)){
                dataSource=(DataSource) dsClass.getConstructor().newInstance();
                // finalDataSource是为了在lambda中对dataSource中进行赋值
                DataSource finalDataSource=dataSource;
                // 反射进行属性赋值
                ReflectionUtils.doWithFields(dsClass,field -> {
                    field.setAccessible(true);
                    field.set(finalDataSource,dataSourceProp.get(field.getName()));
                },field -> {
                    // 过滤掉 其中的一个属性以及为空的属性
                    if(Objects.equals(dataSourceProp.get(DS_TYPE),field.getName())){
                        return false;
                    }
                    return !ObjectUtils.isEmpty(dataSourceProp.get(field.getName()));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
