package com.xc.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class kafkaConfig {

    /**
     * 消费失败，被errorHandler拦截
     * 定义SeekToCurrentErrorHandler，用于消费重试
     * 在消息消费失败时，SeekToCurrentErrorHandler 会将 调用 Kafka Consumer 的 [`#seek(TopicPartition partition, long offset)`](https://github.com/axbaretto/kafka/blob/master/clients/src/main/java/org/apache/kafka/clients/consumer/Consumer.java#L124-L132) 方法，
     * 将 Consumer 对于该消息对应的 TopicPartition 分区的本地进度设置成该消息的位置。
     * [FailedRecordTracker](https://github.com/spring-projects/spring-kafka/blob/master/spring-kafka/src/main/java/org/springframework/kafka/listener/FailedRecordTracker.java)
     * 对每个 Topic 的每个 TopicPartition 消费失败次数进行计数
     * 这样，Consumer 在下次从 Kafka Broker 拉取消息的时候，又能重新拉取到这条消费失败的消息，并且是第一条。
     * @param template
     * @return
     */
    @Bean
    @Primary
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?,?> template){
        // 创建DeadLetterPublishingRecoverer 对象
        // 负责实现在重试到达最大次数时，Consumer 还是消费失败时，该消息就会发送到死信队列
        // 死信的topic为原来的topic增加.DLT后缀 TOPIC.DLT
        ConsumerRecordRecoverer recordRecoverer=new DeadLetterPublishingRecoverer(template);
        // 创建FixedBackedOff对象
        //定义重试次数与间隔
        BackOff backOff=new FixedBackOff(10*1000L,3L);
        // 创建SeekToCurrentErrorHandler
        return new SeekToCurrentErrorHandler(recordRecoverer,backOff);
    }

    //@Bean
    //public NewTopic testTopic(){
    //    return TopicBuilder.name(DemoFirst.DEFAULT_TOPIC).build();
    //    //return TopicBuilder.name(DemoFirst.DEFAULT_TOPIC).partitions(3).replicas(2).compact().build();
    //}
}
