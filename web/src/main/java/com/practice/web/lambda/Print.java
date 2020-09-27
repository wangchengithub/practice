package com.practice.web.lambda;

@FunctionalInterface
interface Print<T> {
    public void print(T x);
}
