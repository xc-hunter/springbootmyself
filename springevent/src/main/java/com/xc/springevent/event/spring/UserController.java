package com.xc.springevent.event.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("asdsa")
    public void publishMessageEvent(){
        publisher.publishEvent(new MessageEvent(""));
    }
}
