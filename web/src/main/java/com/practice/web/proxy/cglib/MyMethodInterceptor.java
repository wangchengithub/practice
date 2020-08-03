package com.practice.web.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib interceptor start");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib interceptor end");
        return object;
    }
}
