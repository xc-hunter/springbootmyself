package com.xc.kafka.consumer;

import com.xc.kafka.message.DemoFirst;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumerFirst {

    /**
     * 消费，可指定消费组
     * topics:指定topic,可指定多个,这使用组管理，Kafka 将为组成员分配分区,与topicPattern、topicPartitions互斥
     * topicPattern:使用匹配模式去动态订阅topic,订阅与指定模式匹配的所有主题，以获取动态分配的分区,
     *              模式匹配将针对检查时存在的主题定期执行。表达式必须解析为主题模式（支持字符串或模式结果类型）。
     *              这使用组管理，Kafka 将为组成员分配分区,与topics、topicPartitions互斥
     * topicPartitions:使用手动主题分区分配,此侦听器的主题分区。与topics、topicPattern互斥,@TopicPartition 注解的数组。每个 @TopicPartition 注解，可配置监听的 Topic、队列、消费的开始位置
     * @param message
     */
    @KafkaListener(topics = {DemoFirst.DEFAULT_TOPIC,"cx"},groupId = "one-"+DemoFirst.DEFAULT_TOPIC)
    public void onMessage(DemoFirst message){
        log.info("线程编号{},消费消息{}",Thread.currentThread().getId(),message);
    }

    @KafkaListener(topics = {DemoFirst.DEFAULT_TOPIC},groupId = "two-"+DemoFirst.DEFAULT_TOPIC)
    public void onMessageTwo(ConsumerRecord<Integer,String> consumerRecord){
        consumerRecord.headers().forEach(header -> System.out.println(header.key()+":::"+header.value()));
        log.info("线程编号{},消费消息{},主题{},分区{},偏移{}",Thread.currentThread().getId(),consumerRecord.value(),consumerRecord.topic(),consumerRecord.partition(),consumerRecord.offset());
    }

}
