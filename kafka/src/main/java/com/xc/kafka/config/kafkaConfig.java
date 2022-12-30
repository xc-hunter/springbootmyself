package com.xc.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
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

    /**
     * RoutingKafkaTemplate
     * use producer by topic pattern
     * @param context
     * @param pf
     * @return
     */
    //@Bean
    //public RoutingKafkaTemplate routingKafkaTemplate(GenericApplicationContext context, ProducerFactory<Object,Object> pf){
    //    // 赋值原有的其他配置信息
    //    Map<String,Object> configs=new HashMap<>(pf.getConfigurationProperties());
    //    // 添加值序列化配置
    //    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    //    // 创建KafkaProducerFactory
    //    DefaultKafkaProducerFactory<Object,Object> bytesPF=new DefaultKafkaProducerFactory<>(configs);
    //    // 注册到上下文
    //    context.registerBean(DefaultKafkaProducerFactory.class,"bytesPF",bytesPF);
    //    // 配置route
    //    Map<Pattern,ProducerFactory<Object,Object>> map=new LinkedHashMap<>();
    //    map.put(Pattern.compile("BYTEA"),bytesPF);
    //    map.put(Pattern.compile(".+"),pf);
    //    // todo 配置文件是否取消序列化配置?
    //    return new RoutingKafkaTemplate(map);
    //}

    /**
     * 测试ReplyingKafkaTemplate
     * @param template
     * @return
     */
    //@Bean
    //public ApplicationRunner runner(ReplyingKafkaTemplate<String, String, String> template) {
    //    return args -> {
    //        // waitForAssignment配合配置 auto.offset.reset=latest,以避免在容器初始化之前发送请求和回复
    //        // spring kafka 2.8.8版本才具备等待waitForAssignment方法
    //        //if (!template.waitForAssignment(Duration.ofSeconds(10))) {
    //        //    throw new IllegalStateException("Reply container did not initialize");
    //        //}
    //        // 初始化测试信息发送到topic为kRequests,content为foo
    //        ProducerRecord<String, String> record = new ProducerRecord<>("kRequests", "foo");
    //        // 发送结果
    //        RequestReplyFuture<String, String, String> replyFuture = template.sendAndReceive(record);
    //        // 获取发送结果 get阻塞获取
    //        SendResult<String, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
    //        System.out.println("Sent ok: " + sendResult.getRecordMetadata());
    //        // RequestReplyFuture get获取到的是通过set设置的内容（可为result、exception）
    //        ConsumerRecord<String, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
    //        System.out.println("Return value: " + consumerRecord.value());
    //    };
    //}

    /**
     * 配置ReplyingKafkaTemplate
     * @param pf 生产者工厂
     * @param repliesContainer 回复消费监听
     * @return
     */
    //@Bean
    //public ReplyingKafkaTemplate<String,String,String> replyingKafkaTemplate(ProducerFactory<String,String> pf, ConcurrentMessageListenerContainer<String,String> repliesContainer){
    //    return new ReplyingKafkaTemplate<>(pf,repliesContainer);
    //}

    ///**
    // * 监听器容器
    // * @param containerFactory
    // * @return
    // */
    //@Bean
    //public ConcurrentMessageListenerContainer<String, String> repliesContainer(
    //        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
    //
    //    // 定义监听的topic,可为数组
    //    ConcurrentMessageListenerContainer<String, String> repliesContainer =
    //            containerFactory.createContainer("kReplies");
    //    // 消费组设置
    //    repliesContainer.getContainerProperties().setGroupId("repliesGroup");
    //    repliesContainer.setAutoStartup(false);
    //    return repliesContainer;
    //}

    @Bean
    public NewTopic kRequests() {
        return TopicBuilder.name("kRequests")
                .partitions(10)
                // 配置的副本数replicas需要保证<=broker数量
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic testTopic(){
        return TopicBuilder.name("test_topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
    //
    //@Bean
    //public NewTopic kReplies() {
    //    return TopicBuilder.name("kReplies")
    //            .partitions(10)
    //            .replicas(2)
    //            .build();
    //}

}
