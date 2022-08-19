package com.xc.springevent.event.spring;

import org.springframework.context.ApplicationEvent;

// 自定义事件
public class MessageEvent extends ApplicationEvent {

    public MessageEvent(Object source) {

        super(source);
    }
}
