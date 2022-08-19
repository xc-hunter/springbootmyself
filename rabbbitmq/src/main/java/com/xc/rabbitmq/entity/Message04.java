package com.xc.rabbitmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//批量消费第二种
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message04 implements Serializable {
    private static final String QUEUE_BASE="QUEUE_BASE-";
    //SON QUEUE
    public static final String QUEUE_0=QUEUE_BASE+"0";
    public static final String QUEUE_1=QUEUE_BASE+"1";
    public static final String QUEUE_2=QUEUE_BASE+"2";
    public static final String QUEUE_3=QUEUE_BASE+"3";

    public static final String EXCHANGE="EXCHANGE_S";

    private Integer id;
}
