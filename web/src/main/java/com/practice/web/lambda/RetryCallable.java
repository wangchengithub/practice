package com.practice.web.lambda;

public interface RetryCallable<T> {

    T call();

}
