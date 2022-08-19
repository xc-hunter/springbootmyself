package com.xc.redis;

import com.xc.redis.tool.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableCaching开启缓存启动功能
@EnableCaching
@Slf4j
public class RedisApplication {

    @Autowired
    private RedisUtil redisUtil;
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
    @PostConstruct
    public void test(){
        redisUtil.set("xc","xc1");
        log.info("设置{}键成功,并获取到值{}","xc",redisUtil.get("xc"));
    }
}
