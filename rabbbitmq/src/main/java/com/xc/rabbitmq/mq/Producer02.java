package com.xc.rabbitmq.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer02 {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String exchange,String routingKey, Object object){
        rabbitTemplate.convertAndSend(exchange,routingKey,object);
    }

}
