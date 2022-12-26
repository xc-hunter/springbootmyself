package com.xc.poddiscovery.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class TestController {

    @GetMapping("/testinfoone")
    public String getTestInfoOne(){
        return "testinfoone";
    }

    @GetMapping("/testinfotwo")
    public String getTestInfoTwo(){
        return "testinfotwo";
    }
}
