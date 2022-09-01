package com.xc.kafka.message;

import lombok.Data;

@Data
public class DemoFirst {

    public static final String DEFAULT_TOPIC="test_topic";

    private int id;
    private String info;
}
