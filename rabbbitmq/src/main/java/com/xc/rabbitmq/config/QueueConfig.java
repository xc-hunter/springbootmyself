package com.xc.rabbitmq.config;

import com.xc.rabbitmq.entity.Message01;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

//实现BeanPostProcessor类，使用Bean的生命周期函数
//达到启动生产者就会自动创建交换机和队列，而不是等到发送消息才创建
@Configuration
public class QueueConfig {

    @Bean(name = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("xc");
    }
    @Bean
    public Queue queue(){
        return new Queue("com.xc.order",true);
    }
    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("com.xc.order.#");
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(Message01.EXCHANGE);
    }
    @Bean
    public Queue directQueue(){
        return new Queue(Message01.QUEUE);
    }
    @Bean
    Binding directBinding(Queue directQueue,DirectExchange directExchange){
        return BindingBuilder.bind(directQueue).to(directExchange).with(Message01.BINDINGKEY);
    }
    @Bean
    public BatchingRabbitTemplate batchRabbitTemplate(ConnectionFactory connectionFactory){
        //创建BatchingStrategy对象，代表批量策略
        //消息最大条数
        int batchSize=100;
        //最大内存
        int bufferLimit=10000;
        //超时时间,毫秒，设置为1分钟
        int timeout=60000;
        BatchingStrategy batchingStrategy=new SimpleBatchingStrategy(batchSize,bufferLimit,timeout);
        //创建TaskScheduler对象，用于实现超时发送的定时器
        TaskScheduler taskScheduler=new ConcurrentTaskScheduler();
        //创建BatchingRabbitTemplate对象
        BatchingRabbitTemplate batchTemplate=new BatchingRabbitTemplate(batchingStrategy,taskScheduler);
        batchTemplate.setConnectionFactory(connectionFactory);
        return batchTemplate;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory){
        //创建SimpleRabbitListenerContainerFactory对象
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory,connectionFactory);
        //额外添加批量消费的属性
        factory.setBatchListener(true);
        return factory;
    }

}
