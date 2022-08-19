package com.xc.mongodb.repository;

import com.xc.mongodb.entiry.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UseTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(UserDao u){
        mongoTemplate.insert(u);
    }
}
