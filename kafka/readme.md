# kafka
## 参考
1. [Spring In Action-8.3](https://potoyang.gitbook.io/spring-in-action-v5/di-8-zhang-fa-song-yi-bu-xiao-xi/8.3-shi-yong-kafka-fa-song-xiao-xi)
2. book
    * Kafka In Action
3. practice
    * [docker compose搭建kafka集群及spring-boot连接](https://juejin.cn/post/6941628825721634823)
4. [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
## Spring In Action-8.3
### 设置Kafka
1. 引入依赖
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```
2. 配置Kafka
```yaml
spring:
  kafka:
    bootstrap-servers:
    - kafka.tacocloud.com:9092
    - kafka.tacocloud.com:9093
    - kafka.tacocloud.com:9094
```
3. 关键Bean KafkaTemplate
* 发送消息,可配置元信息
```java
ListenableFuture<SendResult<K, V>> send(String topic, V data);
ListenableFuture<SendResult<K, V>> send(String topic, K key, V data);
ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, K key, V data);
ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, Long timestamp, K key, V data);
ListenableFuture<SendResult<K, V>> send(ProducerRecord<K, V> record);
ListenableFuture<SendResult<K, V>> send(Message<?> message);
ListenableFuture<SendResult<K, V>> sendDefault(V data);
ListenableFuture<SendResult<K, V>> sendDefault(K key, V data);
ListenableFuture<SendResult<K, V>> sendDefault(Integer partition, K key, V data);
ListenableFuture<SendResult<K, V>> sendDefault(Integer partition, Long timestamp, K key, V data);
```
4. 消费，仅可使用@KafkaListener
* 消息体的获取,使用发送端发送的实体接收
* 或者使用 ConsumerRecord<T>或者Messgae<T>进行接收,获取消息体，同时可以获取header信息(K-V形式)
   * ConsumerRecord.value()
   * Message.getPayload()
```java
// pure primary class
 @KafkaListener(topics="tacocloud.orders.topic")
    public void handle(Order order) {
        ui.displayOrder(order);
    }
// ConsumerRecord
@KafkaListener(topics="tacocloud.orders.topic")
public void handle(Order order, ConsumerRecord<Order> record) {
    log.info("Received from partition {} with timestamp {}",
             record.partition(), record.timestamp());
    ui.displayOrder(order);
}
// Message
@KafkaListener(topics="tacocloud.orders.topic")
public void handle(Order order, Message<Order> message) {
    MessageHeaders headers = message.getHeaders();
    log.info("Received from partition {} with timestamp {}",
             headers.get(KafkaHeaders.RECEIVED_PARTITION_ID)
             headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
    ui.displayOrder(order);
}
```
