package com.kinglead.consumerribbo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user-service")
public interface HelloAction {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String hello();

}
