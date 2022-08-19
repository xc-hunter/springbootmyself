package com.xc.springevent.event.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

//**默认情况下，spring实现的观察者模式，同步阻塞的**。
// 如果想异步publish事件，可以自定义`SimpleApplicationEventMulticaster`，然后构造一下`executor`线程池就好啦
@Component
public class ListenerConfig {
    //@Bean
    //public SimpleApplicationEventMulticaster applicationEventMulticaster(){
    //    SimpleApplicationEventMulticaster simpleApplicationEventMulticaster=new SimpleApplicationEventMulticaster();
    //    simpleApplicationEventMulticaster.setTaskExecutor(simpleAsyncTaskExecutor());
    //    return simpleApplicationEventMulticaster;
    //}

    // 推荐这个依赖注入 而不是方法调用
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(SimpleAsyncTaskExecutor simpleAsyncTaskExecutor){
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster=new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(simpleAsyncTaskExecutor);
        return simpleApplicationEventMulticaster;
    }

    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
