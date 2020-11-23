package com.kinglead.consumerribbo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class HelloService {

    @Resource
    RestTemplate restTemplate;

    public String helloService(){
        return restTemplate.getForObject("http://user-service/hello",String.class);
    }

}
