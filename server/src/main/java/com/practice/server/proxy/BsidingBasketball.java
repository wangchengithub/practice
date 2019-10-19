package com.practice.server.proxy;

public class BsidingBasketball implements Basketball {

    @Override
    public void size() {
        System.out.println("大的");
    }

    @Override
    public void color() {
        System.out.println("黑的");
    }
}
