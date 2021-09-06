package com.practice.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ConsulTestController {

    @Autowired
    private Environment environment;

    @RequestMapping("/instance-id")
    public String instanceId() {
        //throw new RuntimeException("abc");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("done");
        return environment.getProperty("spring.cloud.consul.discovery.instanceId");
    }

    @RequestMapping("/test")
    public String test() {
        log.info("test");
        return "hello web";
    }

    @RequestMapping("/retry")
    public String retry() {
        log.info("retry");
        throw new RuntimeException("error le");
        //return "hello web retry";
    }
}
