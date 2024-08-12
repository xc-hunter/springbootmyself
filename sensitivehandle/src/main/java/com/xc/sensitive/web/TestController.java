package com.xc.sensitive.web;

import com.xc.sensitive.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xc
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping
    public User returnUser(){
        return new User("xctest1213231", "12345678901", "12345678901@163.com");
    }
}
