package com.practice.web;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.practice.dubbo.api.DubboTest;
import com.practice.web.proxy.aspect.AspectTestService;

@RestController
public class TestController {

    @Reference
    private DubboTest dubboTest;

    @Autowired
    private AspectTestService aspectTestService;

    @GetMapping("/test-request-param")
    public LocalDate testRequestParam(@RequestParam LocalDate day) {
        return LocalDate.now();
    }

    @GetMapping("/dubbo-test")
    public void dubboTest() {
        dubboTest.test();
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("remote" + request.getRemoteAddr() + " " + request.getRemotePort());
        System.out.println("X-Real-IP: " + request.getHeader("X-Real-IP"));
        System.out.println("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
        System.out.println("Host:" + request.getHeader("Host"));
        System.out.println("Connection:" + request.getHeader("Connection"));
        System.out.println("web1");
        return "successsuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccesssuccess";
    }

    @GetMapping("/aspect-test")
    public void aspectTest() {
        aspectTestService.aspectTest();
    }
}
