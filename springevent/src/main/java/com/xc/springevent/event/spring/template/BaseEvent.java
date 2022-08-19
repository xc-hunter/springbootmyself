package com.xc.springevent.event.spring.template;

import org.springframework.context.ApplicationEvent;

public class BaseEvent extends ApplicationEvent {

    public BaseEvent(Object source) {
        super(source);
    }

    public BaseEvent(){
        this("");
    }
}
