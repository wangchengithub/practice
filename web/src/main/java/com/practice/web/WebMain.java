package com.practice.web;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.practice.web.dubbo.client")
public class WebMain {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            AbstractHttp11Protocol protocol = ((AbstractHttp11Protocol) connector.getProtocolHandler());
            protocol.setMaxKeepAliveRequests(20);
            //protocol.setKeepAliveTimeout(60000);
        });
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }
}
