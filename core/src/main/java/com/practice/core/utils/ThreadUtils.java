package com.practice.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;

public class ThreadUtils {

    public static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
            // do nothing
        }
    }

    public static <T> void waitAllDone(Iterable<? extends Future<? extends T>> futures) {
        Throwable ex = null;
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Throwable e) {
                ex = e;
            }
        }
        if (ex != null) {
            throw wrapToRuntimeException(ex.getCause());
        }
    }

    public static <T> List<T> invokeAllCallable(ExecutorService executor, Collection<Callable<T>> tasks) {
        List<Future<T>> futures = Lists.newArrayList();
        for (Callable<T> task : tasks) {
            futures.add(executor.submit(task));
        }
        return collectAll(futures);
    }

    public static void invokeAllRunnable(ExecutorService executor, Collection<Runnable> tasks) {
        List<Future<?>> futures = Lists.newArrayList();
        for (Runnable task : tasks) {
            futures.add(executor.submit(task));
        }
        waitAllDone(futures);
    }

    public static <T> List<T> collectAll(Collection<Future<T>> futures) {
        List<T> results = Lists.newArrayList();
        RuntimeException ex = null;
        for (Future<T> f : futures) {
            try {
                results.add(collect(f));
            } catch (Throwable e) {
                ex = wrapToRuntimeException(e);
            }
        }
        if (ex != null) {
            throw ex;
        }
        return results;
    }

    public static <T> T collect(Future<T> future) {
        while (true) {
            try {
                return future.get();
            } catch (InterruptedException e) {
                // 休眠1毫秒，防止万一不能get死循环导致cpu100%
                sleepQuietly(1);
            } catch (Throwable e) {
                throw wrapToRuntimeException(e);
            }
        }
    }

    private static RuntimeException wrapToRuntimeException(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else if (e instanceof ExecutionException) {
            return new RuntimeException(e.getCause());
        } else {
            return new RuntimeException(e);
        }
    }

    public static boolean isFutureNonNullAndNotDone(Future future) {
        return future != null && future.isDone() == false;
    }

}
