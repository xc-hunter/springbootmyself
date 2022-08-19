package com.xc.rabbitmq.mq;

import com.xc.rabbitmq.entity.Message04;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Message04.QUEUE_0)
@RabbitListener(queues = Message04.QUEUE_1)
@RabbitListener(queues = Message04.QUEUE_2)
@RabbitListener(queues = Message04.QUEUE_3)
@Slf4j
public class Consumer04 {
    //在配置了containeFactory为批量消费时，系统将会使用下面这个方法进行消息消费
    @RabbitHandler
    public void onMessage(Message<Message04> message){
        log.info("[ThreadNumber:{}Queue:{} id:{}]",Thread.currentThread().getId(),
                message.getHeaders().get("amqp_consumerQueue",String.class),message.getPayload().getId());
    }
}
