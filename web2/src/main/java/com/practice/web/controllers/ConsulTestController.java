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
        return environment.getProperty("spring.cloud.consul.discovery.instanceId");
    }

    @RequestMapping("/test")
    public String test() {
        return "hello web2";
    }

    @RequestMapping("/retry")
    public String retry() {
        log.info("retry");
        throw new RuntimeException("error le");
        //return "hello web2 retry";
    }
}
