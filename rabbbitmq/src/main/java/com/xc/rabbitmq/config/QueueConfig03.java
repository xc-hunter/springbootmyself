package com.xc.rabbitmq.config;

import com.xc.rabbitmq.entity.Message03;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//实现BeanPostProcessor类，使用Bean的生命周期函数
//达到启动生产者就会自动创建交换机和队列，而不是等到发送消息才创建
@Configuration
public class QueueConfig03 {

    @Bean
    public DirectExchange directExchange03(){
        return new DirectExchange(Message03.EXCHANGE);
    }
    @Bean
    public Queue directQueue03(){
        //此处队列设置配套死信队列
        return QueueBuilder.durable(Message03.QUEUE) //是否持久化
                .exclusive() //是否排它
                .autoDelete() //是否自动删除
                //定义死信时的exchange和routingkey
                .deadLetterExchange(Message03.EXCHANGE)
                .deadLetterRoutingKey(Message03.DEAD_ROUTING_KEY)
                .build();
    }
    @Bean
    Binding directBinding03(Queue directQueue03,DirectExchange directExchange03){
        return BindingBuilder.bind(directQueue03).to(directExchange03).with(Message03.BINDINGKEY);
    }
    @Bean
    public Queue deadQueue(){
        return new Queue(Message03.DEAD_QUEUE);
    }
   @Bean
    Binding deadBinding(Queue deadQueue,DirectExchange directExchange03){
        return BindingBuilder.bind(deadQueue).to(directExchange03)
                .with(Message03.DEAD_ROUTING_KEY);
   }

}
