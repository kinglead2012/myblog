package com.kinglead.consumerribbo;

import com.netflix.ribbon.hystrix.FallbackHandler;
import rx.Observable;

import java.util.Map;

public class SchedualServiceHystric implements FallbackHandler {

    @Override
    public Observable getFallback(com.netflix.hystrix.HystrixInvokableInfo hystrixInvokableInfo, Map map) {
        return null;
    }
}
