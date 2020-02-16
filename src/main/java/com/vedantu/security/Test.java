package com.vedantu.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @RequestMapping(value="/hello")
    public String hello(){
       return "hello world";
    }
}
