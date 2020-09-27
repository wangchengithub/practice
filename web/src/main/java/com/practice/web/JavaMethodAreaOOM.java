package com.practice.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class JavaMethodAreaOOM {

    public static void main(String[] args) throws Exception {
        JavaMethodAreaOOM oom = new JavaMethodAreaOOM();
        System.out.println("xxx");
        for (int i = 0; i < 20; i++) {
            JavaMethodAreaOOM.class.getDeclaredMethod("test1", null).invoke(oom);
        }
        for (int i = 0; i < 20; i++) {
            JavaMethodAreaOOM.class.getDeclaredMethod("test2", null).invoke(oom);
        }
        try {
            while (true) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMObject.class);
                enhancer.setUseCache(false);
                enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invokeSuper(obj, args1));
                enhancer.create();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Arrays.asList(123).forEach(i -> System.out.println(i));
            Thread.sleep(60 * 60 * 1000);
        }
    }


    public List<List<Integer>> subsets(int[] nums) {
        if(nums.length==0){
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList();
        for(int i=0;i<nums.length;i++){
            List<List<Integer>> tm  = new ArrayList();
            for(int j = 0; j< result.size();j++){
                //tm.add(new ArrayList<Integer>(result.get(j)){{add(nums[i]);}});
            }
            result.addAll(tm);
        }
        return result;
    }

    static class OOMObject {

    }

    public void test1() {
        System.out.println("1");
    }

    public void test2() {
        System.out.println("2");
    }
}
