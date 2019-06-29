package com.practice.web.json;

import java.util.concurrent.TimeUnit;

public class Test {

    private static boolean isStop;

    public static void main(String[] args) throws Exception {
        Object o = new Object();
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!isStop) {
                i++;
                System.out.println(i);
            }
            o.notifyAll();
        });
        thread.start();

        TimeUnit.SECONDS.sleep(3);
        isStop = true;
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println("start");
        o.wait();
        System.out.println("finished");
    }
}
