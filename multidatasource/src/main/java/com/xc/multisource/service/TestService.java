package com.xc.multisource.service;

import com.xc.multisource.annotation.DataSourceSelector;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @DataSourceSelector("master")
    public void insert(){

    }

    @DataSourceSelector("default")
    public void query(){

    }
}
