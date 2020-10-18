package com.practice.web.lambda;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

public class LambdaTestB {

    public long test(Map a, String b) {
        System.out.println(a + b);
        return RandomUtils.nextInt();
    }
}
