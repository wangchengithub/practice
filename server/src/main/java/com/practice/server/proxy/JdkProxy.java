package com.practice.server.proxy;

import java.lang.reflect.Proxy;

public class JdkProxy {

    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        BsidingBasketball bsidingBasketball = new BsidingBasketball();
        BasketballJdkProxyInvocationHandler handler = new BasketballJdkProxyInvocationHandler(bsidingBasketball);
        Basketball proxy = (Basketball) Proxy.newProxyInstance(bsidingBasketball.getClass().getClassLoader(), bsidingBasketball.getClass().getInterfaces(), handler);
        proxy.color();
        proxy.size();
        Basketball proxy2 = (Basketball) Proxy.newProxyInstance(bsidingBasketball.getClass().getClassLoader(), bsidingBasketball.getClass().getInterfaces(), handler);
        proxy2.color();
        proxy2.size();
        System.out.println(Proxy.getProxyClass(Basketball.class.getClassLoader(), Basketball.class));
    }
}
