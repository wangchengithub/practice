package com.practice.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/circuit")
public class CircuitBreakerController {

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    private RestTemplate rest;

    @RequestMapping("/slow")
    public String circuit() {
        return cbFactory.create("slow").run(() -> rest.getForObject("http://localhost:9090/api/instance-id", String.class),
                throwable -> "fallback");
    }
}
