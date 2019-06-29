package com.practice.web;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.practice.web.dubbo.client")
public class WebMain {

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }
}
