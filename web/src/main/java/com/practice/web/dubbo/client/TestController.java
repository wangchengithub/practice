package com.practice.web.dubbo.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.practice.dubbo.api.DubboTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@RestController
public class TestController {

    @Reference
    private DubboTest dubboTest;

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

    @GetMapping("/url-test")
    public String test(String b, String redirect) {
        return redirect + "      " + b;
    }

    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("http://baidu.com?a=c&b=e"));
    }
}
