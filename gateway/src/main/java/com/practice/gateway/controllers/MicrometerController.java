package com.practice.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/micrometer")
@Timed
public class MicrometerController {

    @Timed
    @GetMapping("/1")
    //@Timed(extraTags = { "region", "us-east-1" })
    //@Timed(value = "all.people", longTask = true)
    public void test() throws Exception {
        Thread.sleep(1000);
        System.out.println("micrometer done");
    }
}
