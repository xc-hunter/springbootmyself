package com.xc.springevent.event.spring.template;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterMessageListenerImpl implements IEventListener<RegisterMessageEvent>{

    @Override
    public void handler(RegisterMessageEvent registerMessageEvent) {
        System.out.println(""+registerMessageEvent.getSource() + new Date());
    }
}
