package com.practice.web.dubbo.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.practice.web.dubbo.api.DubboTest;
import org.springframework.stereotype.Component;

@Component
@Service
public class DubboTestService implements DubboTest {

    @Override
    public void test() {
        System.out.println("dubbo connect success");
    }
}
