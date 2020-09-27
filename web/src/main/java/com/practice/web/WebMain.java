package com.practice.web;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cloud.gateway.handler.predicate.BeforeRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.practice.web.dubbo.client")
@Import({WebConfiguration.class})
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

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, BeforeRoutePredicateFactory beforeRoutePredicateFactory) {
        return builder.routes()
                .route("path_route", r -> r.path("/get").uri("http://httpbin.org"))
                .route("host_route", r -> r.host("*.myhost.org").uri("http://httpbin.org"))
                .route("rewrite_route", r -> r.host("*.rewrite.org").filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}")).uri("http://httpbin.org"))
                .route("hystrix_route", r -> r.host("*.hystrix.org").filters(f -> f.hystrix(c -> c.setName("slowcmd"))).uri("http://httpbin.org"))
                .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org").filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback"))).uri("http://httpbin.org"))
                .route("limit_route", r -> r.host("*.limited.org").and().path("/anything/**")/*.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))*/.uri("http://httpbin.org").predicate(beforeRoutePredicateFactory.apply(new BeforeRoutePredicateFactory.Config())))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }
}
