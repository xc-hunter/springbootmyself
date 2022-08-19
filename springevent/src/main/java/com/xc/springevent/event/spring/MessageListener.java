package com.xc.springevent.event.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

// define event listener
@Component
// consume async
public class MessageListener implements ApplicationListener<MessageEvent> {

    @Override
    @Async()
    public void onApplicationEvent(MessageEvent messageEvent) {
        System.out.println("messageEvent happen");
    }
}
