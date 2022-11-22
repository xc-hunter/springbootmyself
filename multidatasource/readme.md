多数据源Starter
* 基于AbstractRoutingDataSource实现路由多数据源
  * determineCurrentLookupKey 获取ThreadLocal的路由key
* 基于AOP来实现Thread绑定不同的路由key
  * 注解来定义当前线程的数据源路由key，存在默认值
* 配置文件配置多数据源（参考配置文件的双重Map如何在yaml文件中配置）
* META_INF/spring.factories文件配合Spring Boot AutoConfiguration基于SPI实现自动配置
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.xc.multisource.config.MultiDataSourceConfig
```
* Class.forName().newInstance()创建实例,ReflectionUtils.doWithFields进行属性填充以及属性过滤
相关文章
* [difference between DataSource And DirverManager]()https://zhangguodong.me/2017/12/03/Datasource%E5%92%8CDriverManager%E8%8E%B7%E5%8F%96Connection%E7%9A%84%E5%8C%BA%E5%88%AB/
* [JDBC-DriverManager-Connect-Demo](https://www.runoob.com/java/java-mysql-connect.html)
