package com.xc.rabbitmq.mq;

import com.xc.rabbitmq.entity.Message01;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RabbitListener(queues = Message01.QUEUE,
containerFactory = "consumerBatchContainerFactory")
@Slf4j
public class Consumer {
    @RabbitHandler
    public void onMessage(Message01 message01){
        log.info("[线程编号：{} 消息内容：{}]",Thread.currentThread().getId(),message01);
    }
    //在配置了containeFactory为批量消费时，系统将会使用下面这个方法进行消息消费
    @RabbitHandler
    public void onBatchMessage(List<Message01> batchMess){
        log.info("批量消费，线程编号：{}，消息数量：{}",Thread.currentThread(),batchMess.size());
    }
}
