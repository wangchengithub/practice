package com.practice.web.lambda;

public class LambdaTest {

    public static void printString(String s, Print<String> print) {
        print.print(s);
    }
    //-Djdk.internal.lambda.dumpProxyClasses -verbose:class -Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            printString("test" + i, (x) -> {
                System.out.println(x);
            });
        }
    }

}
