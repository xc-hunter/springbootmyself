package com.xc.rabbitmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//批量消费第二种
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message03 implements Serializable {
    public static final String QUEUE="XC_QUEUE_03";
    //死信队列
    public static final String DEAD_QUEUE="DEAD_XC_QUEUE_03";
    public static final String EXCHANGE="XC_EXCHANGE_03";
    public static final String BINDINGKEY="xc.route.03";
    //死信binding key
    public static final String DEAD_ROUTING_KEY="dead.xc.route.03";
    private Integer id;
}
