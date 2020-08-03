package com.practice.web.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BasketballJdkProxyInvocationHandler implements InvocationHandler {

    private Basketball basketball;

    BasketballJdkProxyInvocationHandler(Basketball basketball) {
        this.basketball = basketball;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy start");
        method.invoke(basketball, args);
        System.out.println("proxy end");
        return null;
    }
}
