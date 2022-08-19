package com.xc.rabbitmq.mq;

import com.xc.rabbitmq.entity.Message03;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Message03.QUEUE)
@Slf4j
public class Consumer03 {
    //在配置了containeFactory为批量消费时，系统将会使用下面这个方法进行消息消费
    @RabbitHandler
    public void onMessage(Message03 message03){
        log.info("重试消费，线程编号：{}，消息：{}",Thread.currentThread(),message03);
        throw new RuntimeException("模拟消费失败");
    }
}
