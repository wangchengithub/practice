package com.practice.server.dubbo;

import com.practice.dubbo.api.DubboTest;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(dynamic = true)
public class DubboTestService implements DubboTest {

    @Override
    public void test() {
        System.out.println("dubbo connect success");
    }
}
