package com.xc.springevent.event.self;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
public class UserController implements ApplicationContextAware {

    private List<RegisterObserver> observers;

    private Executor executor= Executors.newFixedThreadPool(10);

    @RequestMapping("msg")
    public String register(){
        // sync notify observers
        syncNotify("msg");
        asyncNotify("msg");
        return "";
    }

    private void asyncNotify(String msg) {
        observers.forEach(a->{
            executor.execute(()->{
                a.handleEvent(msg);
            });
        });
    }

    private void syncNotify(String msg) {
        observers.forEach(a->a.handleEvent(""));
    }

    // 注册观察者 应该是可以直接通过@Autowired注入的
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        observers=new ArrayList<>(applicationContext.getBeansOfType(RegisterObserver.class).values());
    }
}
