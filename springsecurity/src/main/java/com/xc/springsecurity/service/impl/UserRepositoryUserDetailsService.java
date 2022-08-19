package com.xc.springsecurity.service.impl;

import com.xc.springsecurity.dao.UserRepository;
import com.xc.springsecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user!=null){
            //可以在此处为UserDetails添加权限
            return user;
        }
        throw new UsernameNotFoundException("User '"+username+"' not found");
    }
}
