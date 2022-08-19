package com.xc.springevent.event.spring.template;

import org.springframework.context.ApplicationListener;

public interface IEventListener<T extends BaseEvent> extends ApplicationListener<T> {

    @Override
    default void onApplicationEvent(T t){
        try {
            if(support(t)){
                handler(t);
            }
        } catch (Exception e) {
            handleExeception(e);
        }
    }

    void handler(T t);

    // default support
    default boolean support(T t){
        return true;
    }

    // empty impl
    default void handleExeception(Exception e){

    }
}
