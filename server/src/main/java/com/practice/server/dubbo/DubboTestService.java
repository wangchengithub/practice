package com.practice.server.dubbo;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import com.practice.dubbo.api.DubboTest;

@Component
@Service(dynamic = true)
public class DubboTestService implements DubboTest {

    @Override
    public void test() {
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("dubbo connect success");
    }
}
