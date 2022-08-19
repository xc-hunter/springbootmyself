package com.xc.springsecurity.dao;

import com.xc.springsecurity.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
