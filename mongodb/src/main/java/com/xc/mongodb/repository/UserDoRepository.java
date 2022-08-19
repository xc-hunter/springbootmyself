package com.xc.mongodb.repository;

import com.xc.mongodb.entiry.UserDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDoRepository extends MongoRepository<UserDo,Long> {
}
