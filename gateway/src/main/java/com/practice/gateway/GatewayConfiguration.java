package com.practice.gateway;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.handler.predicate.BeforeRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GatewayConfiguration {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build()).build());
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> slowCustomizer() {
        return factory -> factory.addCircuitBreakerCustomizer(circuitBreaker -> circuitBreaker.getEventPublisher()
                .onError((event) -> System.out.println("error")).onSuccess((event -> System.out.println("success"))), "slow");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 3, 60);
    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, BeforeRoutePredicateFactory beforeRoutePredicateFactory) {
        //@formatter:off
        return builder.routes()
                .route("path_route", r -> r
                        .host("localhost:8090").and().path("/api/test")
                        .uri("http://localhost:9091"))
                .route("limit_route", r -> r
                        .host("localhost:8090").and().path("/api/instance-id")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                        .uri("http://localhost:9091/api/instance-id"))
                .route("retry-test", r -> r.path("/api/retry")
                        .filters(f -> f.retry(3))
                        .uri("lb://web"))
                .route("fallback-test", r -> r.path("/fall")
                        .filters(f -> f.circuitBreaker(c -> c.setName("myCircuitBreaker")))
                        .uri("http://localhost:9090"))
                //直接访问http://localhost:8090/web/api/instance-id?user=1可访问consul中的服务
                .build();
    }
}
