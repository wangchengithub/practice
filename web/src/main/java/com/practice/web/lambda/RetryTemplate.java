package com.practice.web.lambda;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Throwables;

public class RetryTemplate {

    private int retryCount;

    private Set<Class<? extends Throwable>> retryPolicy;

    public RetryTemplate(int retryCount, Collection<Class<? extends Throwable>> retryPolicy) {
        this.retryCount = retryCount;
        this.retryPolicy = new HashSet<>(retryPolicy);
    }

    @SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
    public <T> T executeWithRetry(RetryCallable<T> callable) {
        for (int i = 0; i < retryCount; i++) {
            try {
                return callable.call();
            } catch (Throwable e) {
                if (canNotRetry(i, e)) {
                    throw e;
                }
            }
        }
        throw new AssertionError();
    }

    public void executeWithRetry(RetryRunnable runnable) {
        RetryCallable<Void> callable = () -> {
            runnable.run();
            return null;
        };
        executeWithRetry(callable);
    }

    private boolean canNotRetry(int i, Throwable e) {
        return i == retryCount - 1 || !(retryPolicy.contains(e.getClass()) || retryPolicy.contains(Throwables.getRootCause(e).getClass()));
    }

}
