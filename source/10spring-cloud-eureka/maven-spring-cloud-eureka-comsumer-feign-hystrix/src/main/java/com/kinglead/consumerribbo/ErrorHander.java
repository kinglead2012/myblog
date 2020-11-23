package com.kinglead.consumerribbo;

import org.springframework.stereotype.Component;

@Component
public class ErrorHander implements HelloAction {
    @Override
    public String hello() {
        return "feign error";
    }
}
