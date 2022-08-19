package com.xc.rabbitmq.mq;

import com.xc.rabbitmq.entity.Message04;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer04 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id){
        Message04 message04=new Message04(id);
        rabbitTemplate.convertAndSend(Message04.EXCHANGE,Integer.toString(id),message04);
    }
}
