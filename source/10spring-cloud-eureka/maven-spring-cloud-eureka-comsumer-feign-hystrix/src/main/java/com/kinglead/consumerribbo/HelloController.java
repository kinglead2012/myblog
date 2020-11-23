package com.kinglead.consumerribbo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    HelloAction helloAction;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return helloAction.hello();
    }

}
