package com.xc.rabbitmq.mq;

import com.xc.rabbitmq.entity.Message03;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer03 {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void doSend(String exchange,String routingKey, Object object){
        rabbitTemplate.convertAndSend(exchange,routingKey,object);
    }
    public void send(int id){
        Message03 message03=new Message03(id);
        doSend(Message03.EXCHANGE,Message03.BINDINGKEY,message03);
    }

}
