package com.practice.web.lambda;

import java.util.concurrent.Callable;

public class LambdaTestA {

    public void test(Callable<Long> runnable) {
        try {
            runnable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
