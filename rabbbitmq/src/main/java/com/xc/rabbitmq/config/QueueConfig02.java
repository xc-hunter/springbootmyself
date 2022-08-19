package com.xc.rabbitmq.config;

import com.xc.rabbitmq.entity.Message02;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//实现BeanPostProcessor类，使用Bean的生命周期函数
//达到启动生产者就会自动创建交换机和队列，而不是等到发送消息才创建
@Configuration
public class QueueConfig02 {

    @Bean
    public DirectExchange directExchange02(){
        return new DirectExchange(Message02.EXCHANGE);
    }
    @Bean
    public Queue directQueue02(){
        return new Queue(Message02.QUEUE);
    }
    @Bean
    Binding directBinding02(Queue directQueue02,DirectExchange directExchange02){
        return BindingBuilder.bind(directQueue02).to(directExchange02).with(Message02.BINDINGKEY);
    }
    @Bean
    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory02(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory){
        //创建SimpleRabbitListenerContainerFactory对象
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory,connectionFactory);
        //额外添加批量消费的属性
        factory.setBatchListener(true);
        factory.setBatchSize(10);
        factory.setReceiveTimeout(30*1000L);
        factory.setConsumerBatchEnabled(true);
        return factory;
    }

}
