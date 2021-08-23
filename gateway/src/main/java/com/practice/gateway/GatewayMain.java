package com.practice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@EnableDiscoveryClient
@SpringBootApplication
@LoadBalancerClients(defaultConfiguration = {CustomLoadBalancerConfiguration.class}
        , value = {@LoadBalancerClient(value = "consul", configuration = CustomLoadBalancerConfiguration.class)})

public class GatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);
    }

}
