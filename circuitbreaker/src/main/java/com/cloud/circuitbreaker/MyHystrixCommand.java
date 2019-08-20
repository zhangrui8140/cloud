package com.cloud.circuitbreaker;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class MyHystrixCommand<T> extends HystrixCommand<T> {

    protected MyHystrixCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected T run() throws Exception {
        return null;
    }

    @Override
    protected T getFallback() {
        return super.getFallback();
    }
}
