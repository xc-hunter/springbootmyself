package com.xc.mongodb.repository;

import com.xc.mongodb.entiry.UserDao;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDao,Long> {
    //使用Example查询,直接匹配
    default UserDao findByUsername01(String username){
        //构建Example对象，使用username查询
        //包含probe探针构建
        UserDao probe=new UserDao();
        //通过用户名查询，直接匹配
        probe.setUsername(username);
        Example<UserDao> example=Example.of(probe);
        return findOne(example).orElse(null);
    }
    //使用Example查询,模糊匹配
    default UserDao findByUsernameLike(String username){
        //构建Example对象，使用username查询
        //包含probe探针构建
        UserDao probe=new UserDao();
        //通过用户名查询,
        probe.setUsername(username);
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withMatcher("username",
                        ExampleMatcher.GenericPropertyMatchers.contains());
        Example<UserDao> example=Example.of(probe,matcher);
        return findOne(example).orElse(null);
    }
}
