package com.xc.springevent.event.self;

import org.springframework.stereotype.Service;

@Service
public class ObserverTwoIMpl implements RegisterObserver{

    @Override
    public void handleEvent(String msg) {
        System.out.println("observer two impl");
    }
}
