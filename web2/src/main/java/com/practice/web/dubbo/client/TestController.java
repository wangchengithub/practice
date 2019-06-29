package com.practice.web.dubbo.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {

    @GetMapping
    public void test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("remote" + request.getRemoteAddr() + " " + request.getRemotePort());
        System.out.println("X-Real-IP: " + request.getHeader("X-Real-IP"));
        System.out.println("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
        System.out.println("Host:" + request.getHeader("Host"));
        System.out.println("web2");
    }
}
