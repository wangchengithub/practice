package com.practice.web.lambda;

import java.util.Arrays;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

public class LambdaTest {

    private RetryTemplate retryTemplate = new RetryTemplate(3,
            Arrays.asList(ObjectOptimisticLockingFailureException.class, DataIntegrityViolationException.class));

    private LambdaTestA a = new LambdaTestA();

    private LambdaTestB b = new LambdaTestB();

    public static void printString(String s, Print<String> print) {
        print.print(s);
    }
    //-Djdk.internal.lambda.dumpProxyClasses -verbose:class -Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true

    public long test(Map map, String str2, String str3) {
        return retryTemplate.executeWithRetry(() -> b.test(map, str2));
            /*printString("test" + i, (x) -> {
                System.out.println("2" + x + a);
            });*/
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
