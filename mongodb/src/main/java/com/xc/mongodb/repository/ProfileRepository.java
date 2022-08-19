package com.xc.mongodb.repository;

import com.xc.mongodb.entiry.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile,Long> {
}
