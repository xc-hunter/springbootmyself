package com.xc.rabbitmq.util;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqUtil {


    @Autowired
    RabbitAdmin rabbitAdmin;

    private MqUtil(){

    }

    public static class MqUtilHolder{
        private static final MqUtil MQ_UTIL_INSTANCE=new MqUtil();
    }

    public static MqUtil getInstance(){
        return MqUtilHolder.MQ_UTIL_INSTANCE;
    }

    //创建队列
    public void addQueue(Queue queue){
        rabbitAdmin.declareQueue(queue);
    }

    //创建交换机

    public void addExchange(Exchange exchange){
        rabbitAdmin.declareExchange(exchange);
    }

    //创建绑定
    public void addBinding(TopicExchange exchange, Queue queue, String bindingKey){
        Binding bind= BindingBuilder.bind(queue).to(exchange).with(bindingKey);
        rabbitAdmin.declareBinding(bind);
    }




}
