package com.kinglead.consumerribbo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    HelloService helloService;

    @RequestMapping("/hello")
    public String hello(){
        return helloService.helloService();
    }

}
