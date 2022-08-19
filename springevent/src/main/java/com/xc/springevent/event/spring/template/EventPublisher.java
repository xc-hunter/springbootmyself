package com.xc.springevent.event.spring.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    // applicationContext- extend ApplicationEventPublisher
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ThreadExecutorPool threadExecutorPool;

    // sync
    public void publish(BaseEvent event){
        applicationContext.publishEvent(event);
    }

    public void asyncPublish(BaseEvent event){
        threadExecutorPool.threadPoolTaskExecutor().execute(()->applicationContext.publishEvent(event));
    }
}
