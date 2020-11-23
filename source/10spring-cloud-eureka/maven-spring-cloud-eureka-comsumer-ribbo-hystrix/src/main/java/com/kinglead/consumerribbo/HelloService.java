package com.kinglead.consumerribbo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class HelloService {

    @Resource
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error",
            commandProperties = {
                @HystrixProperty(name = "execution.timeout.enabled", value = "false")
            })
    public String helloService(){
        return restTemplate.getForObject("http://user-service/hello",String.class);
    }

    public String error(){
        return "error";
    }

}
