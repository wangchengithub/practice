package com.practice.gateway.controllers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/reactor")
public class ReactorController {

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
}
