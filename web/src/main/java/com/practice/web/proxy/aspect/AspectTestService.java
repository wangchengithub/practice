package com.practice.web.proxy.aspect;

import org.springframework.stereotype.Service;

@Service
public class AspectTestService {

    public void aspectTest() {
        System.out.println("service");
    }
}
