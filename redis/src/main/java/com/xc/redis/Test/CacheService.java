package com.xc.redis.Test;

import com.xc.redis.config.RedisConfig;
import com.xc.redis.entity.CacheBean;
import com.xc.redis.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class CacheService {
    private RedisUtil redisUtil;
    @Autowired
    public CacheService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    //@CachePut：新增方法上，增
    //@Cacheable:查询方法上，查
    //@CacheEvict：更新或者删除上，删改
    @CacheEvict(value = RedisConfig.REDIS_KEY_DATABASE,key = "'cachebean:'+#id")
    public Long update(Long id){
        return id;
    }
    @RequestMapping("/delete")
    @ResponseBody
    @CacheEvict(value = RedisConfig.REDIS_KEY_DATABASE,key = "'cachebean:'+#id")
    public Long delete(@RequestParam Long id){
        System.out.println("进入delte方法");
        return id;
    }
    //查询
    //实际测试，存入Redis的键为 cache::cachebean:id
    @RequestMapping("/query")
    @ResponseBody
    @Cacheable(value = RedisConfig.REDIS_KEY_DATABASE,key = "'cachebean:'+#id",unless = "#result==null")
    public CacheBean getItem(@RequestParam Long id){
        System.out.println("进入query方法");
        return new CacheBean(id,"dec"+id);

    }
}
