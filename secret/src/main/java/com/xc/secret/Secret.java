package com.xc.secret;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Secret {

    @Autowired
    private Environment environment;

    @Value("${password}")
    private String pas;

    public static void main(String[] args) {
        SpringApplication.run(Secret.class,args);
    }

    @PostConstruct
    public void get(){
        String aa= environment.getProperty("password");
        System.out.println(pas);
        System.out.println(aa);
    }
}
