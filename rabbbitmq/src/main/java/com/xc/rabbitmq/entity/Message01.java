package com.xc.rabbitmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message01 implements Serializable {
    public static final String QUEUE="XC_QUEUE_01";
    public static final String EXCHANGE="XC_EXCHANGE_01";
    public static final String BINDINGKEY="xc.route.01";
    private Integer id;
}
