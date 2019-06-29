package com.practice.web.dubbo.server;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.practice.dubbo.server")
public class DubboServerMain {

    public static void main(String[] args) {
        SpringApplication.run(DubboServerMain.class, args);
    }
}
