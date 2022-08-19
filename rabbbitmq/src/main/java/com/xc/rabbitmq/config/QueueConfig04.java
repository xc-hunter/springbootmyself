package com.xc.rabbitmq.config;

import com.xc.rabbitmq.entity.Message04;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig04 {

    @Bean
    public DirectExchange directExchange04(){
        return new DirectExchange(Message04.EXCHANGE);
    }
    @Bean
    public Queue queue0400(){
        return new Queue(Message04.QUEUE_0);
    }
    @Bean
    public Queue queue0401(){
        return new Queue(Message04.QUEUE_1);
    }
    @Bean
    public Queue queue0402(){
        return new Queue(Message04.QUEUE_2);
    }
    @Bean
    public Queue queue0403(){
        return new Queue(Message04.QUEUE_3);
    }
    @Bean
    public Binding binding0400(){
        return BindingBuilder.bind(queue0400()).to(directExchange04()).with("0");
    }
    @Bean
    public Binding binding0401(){
        return BindingBuilder.bind(queue0401()).to(directExchange04()).with("1");
    }
    @Bean
    public Binding binding0402(){
        return BindingBuilder.bind(queue0402()).to(directExchange04()).with("2");
    }
    @Bean
    public Binding binding0403(){
        return BindingBuilder.bind(queue0403()).to(directExchange04()).with("3");
    }
}
