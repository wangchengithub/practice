package com.practice.web.lambda;

import java.util.HashMap;
import java.util.UUID;

public class LambdaTestMain {

    public static void main(String[] args) throws Exception {
        System.out.println("11111111111");
        LambdaTest test = new LambdaTest();
        /*ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int j = 0; j < 50; j++) {
            executorService.submit(() -> {

                for (int i = 0; i < 1000; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    test.test(new HashMap(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
         /*       }
            });
        }*/

        /*A a = new A();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> future = executorService.submit(() -> {
            a.thread = Thread.currentThread();
            for (int i = 0; i < 1000000000; i++) {
                try {
                    System.out.println("0");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("255");
                }
            }
        });
        executorService.submit(() -> {
            try {
                Thread.sleep(500);
                System.out.println("interupt");
                a.thread.interrupt();
            } catch (Exception e) {
                System.out.println("a");
            }

        });

        System.out.println("get");
        while (true) {
            try {
                future.get();
                System.out.println("finish");
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("11");
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println("112");
            }
        }*/

    }

    public static class A {
        Thread thread;
    }

}
