package com.xc.rabbitmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//批量消费第二种
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message02 implements Serializable {
    public static final String QUEUE="XC_QUEUE_02";
    public static final String EXCHANGE="XC_EXCHANGE_02";
    public static final String BINDINGKEY="xc.route.02";
    private Integer id;
}
