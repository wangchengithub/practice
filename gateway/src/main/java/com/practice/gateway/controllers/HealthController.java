package com.practice.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/actuator")
public class HealthController {

    @GetMapping("/health")
    public void health() {
        System.out.println("1");
        log.info("health");
    }

}
