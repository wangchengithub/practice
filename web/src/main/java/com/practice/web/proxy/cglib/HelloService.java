package com.practice.web.proxy.cglib;

public class HelloService {

    public void hello() {
        System.out.println("hello cglib");
    }

    final public void finalHello() {
        System.out.println("final hello");
    }
}
