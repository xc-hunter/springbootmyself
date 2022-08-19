# Spring Batch
## 使用场景
需要使用批处理才能完成的任务
* 自动化处理大批量复杂数据，如月结计算
* 重复性处理大批量数据，如费率计算
* 充当内部系统和外部系统的数据纽带，中间需要对数据进行格式化、校验、转换处理等
## 包含示例
* 任务单步骤
* 任务多步骤
* 任务多步骤，多步骤之间依据步骤执行情况决定后续步骤执行
* 任务步骤，flow和step区别
* 并行执行，关键方法split指定异步执行器，并将步骤添加到异步执行器中执行
* 自定义步骤执行决策器，自定义实现复杂的步骤执行逻辑
* 任务嵌套，主要是存在一个将job转换为step的过程
## 步骤 step
* 基本的执行单元，其唯一性由job名称和step名称组合保证
## 参考
* [spring batch官方文档](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/spring-batch-intro.html#spring-batch-intro)
* [本次demo参考](https://mrbird.cc/Spring-Batch%E5%85%A5%E9%97%A8.html)
