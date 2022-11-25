package com.xc.mongodb.repository;

import com.xc.mongodb.entiry.TestName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestNameRepository extends MongoRepository<TestName,Long> {
}
