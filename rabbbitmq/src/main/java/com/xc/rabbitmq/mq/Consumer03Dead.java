package com.xc.rabbitmq.mq;

import com.xc.rabbitmq.entity.Message03;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Message03.DEAD_QUEUE)
@Slf4j
public class Consumer03Dead {

    @RabbitHandler
    public void onBatchMessage(Message03 message03){
        log.info("死信队列消费，线程编号：{}，消息：{}",Thread.currentThread(),message03);
    }
}
