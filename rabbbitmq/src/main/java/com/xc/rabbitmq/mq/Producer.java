package com.xc.rabbitmq.mq;

import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BatchingRabbitTemplate batchingRabbitTemplate;

    public void send(String exchange,String routingKey, Object object){
        rabbitTemplate.convertAndSend(exchange,routingKey,object);
    }

    public void batchSend(String exchange,String routingKey,Object object){
        batchingRabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

}
