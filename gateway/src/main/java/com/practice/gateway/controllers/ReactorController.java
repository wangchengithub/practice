package com.practice.gateway.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("/reactor")
public class ReactorController {

    private Object lock = new Object();

    private WebClient webClient = WebClient.create("http://localhost:8090");

    private ExecutorService executorService = Executors.newFixedThreadPool(50);

    //@Scheduled(fixedDelay = 1000 * 10)
    public void aa() {
        System.out.println("1212");
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            runnableList.add(() -> {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                webClient.get().uri("/reactor/test").retrieve().bodyToMono(String.class).block();
                stopWatch.stop();
                System.out.println("takes:{}" + stopWatch.getTotalTimeSeconds());
            });
        }
        ThreadUtils.invokeAllRunnable(executorService, runnableList);
    }

    @GetMapping("/test")
    public Mono<String> test2() {
        //synchronized (lock) {
        return Mono.fromCallable(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                return "abc";
            } catch (Exception e) {
                return "";
            }
        }).publishOn(Schedulers.elastic());
        // }
    }

    @GetMapping
    public void test() {
        //Flux.just(1, 2, 3).subscribe((su) -> System.out.println(su));
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8).log().subscribe(new Subscriber<Integer>() {

            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(2);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
                s.request(2);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void main(String[] args) {
        System.out.println(HealthController.class.getSimpleName());
    }
}
