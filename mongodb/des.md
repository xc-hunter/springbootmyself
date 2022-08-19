# MongoDB
## 介绍
MongoDB：是NoSQL数据库中最像关系数据库的
* 支持的数据格式类似于json的bjson格式，可以存储复杂的数据类型
* 支持的查询语言强大，语法类似于面向对象的查询语言，支持对数据建立索引
### MySQL和MongoDB概念关联
* 库DataBase----库DataBase
* 表Table-------集合Collection
* 行Row---------文档Document
* 列Column------字段Field
* 关联joins-----嵌入文档或者链接
### Spring Boot操作MongiDB
1. Spring Data Repository形式
2. MongoTemplate
## 实操
本次示例不是采用响应式编程，而是传统编程方式
[docker安装MongoDB](https://segmentfault.com/a/1190000020525887)
### 基于方法名查询
findBy、existsBy、countBy、deleteBy开头方法，后面接具体的条件
* 分页查询，可以借助Pageable类
### 基于Example查询
Spring Data Example
* Example可以创建动态查询，不需要编写包含字段名的查询
* Example不需要使用特定的存储器的查询语句
<br>
不同的Spring Data 实现框架在实际使用时会将Spring Data Example条件解析成相应的查询对象，如
* Spring Data Jpa将Example转换为Predicate
* Spring Data MongoDB将Example转换为Query
<br>
不是所有的Spring Data实现框架都支持Example，Spring Data Elasticsearch就不支持。一般支持的，其Repository都继承了QueryByExampleExecutor接口
#### Example API
* Probe：探针，含有对应字段的实体对象，通过设置该实体对象的字段，作为查询字段
  * Probe不是一个类，而是实体对象的泛指，Example\<T\>中的泛型 T probe
* ExampleMatcher：定义特定字段的匹配模式，如全模糊匹配、前缀模糊匹配等
* Example：Probe和ExampleMatcher的组合，构成查询对象
### 基于MongoTemplate
## 自增主键
MongoDB自带的主键选择是ObjectId类型，需要占用12字节。占用空间较大，绝大多数情况下4字节Int或者8字节Long够用，更适用
* 日志记录上，采用ObjectID为主
### 实现MongoDB自增主键
* 创建一个名为sequence集合，即需要自己创建一个集合，用来为其他集合提供主键
  * seqid:String类型，集合的实体类名
  * value：Long类型，存储每个集合的自增序列
在程序中，每次插入实体对象到 MongoDB 之前，通过 $inc 操作，从 "sequence" 自增获得最新的 ID ，然后将该 ID 赋值给实体对象，最终在插入到 MongoDB 之中。
<br>
对于需要自实现自增主键的集合， 
1. 使用@AutoIncKey注解在ID属性上
2. 创建IncIdEntity抽象基类，需要自增主键的实体继承它
<br>
两种方法都是一种标识，用来标识出需要自增主键的实体，没有标记的实体就采用默认的自增主键。
<br>
定义组件继承AbstractMongoEventListener类，复写onBeforeConvert方法，在转换前将实体对象的id进行处理。
## tips
* 使用MongoDB默认自增主键，类型为Objectid,12字节，且无序
```text
对应实体类中的Id字段需要为String类型
"_id" : ObjectId("6103a1409553ef62cf57915f")
```
