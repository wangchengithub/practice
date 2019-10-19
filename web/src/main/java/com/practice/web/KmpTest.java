package com.practice.web;

import java.util.Arrays;

public class KmpTest {

    public static void main(String[] args) {
        String target = "ABCDABD";
        System.out.println(repeatNum(target));
        System.out.println(Arrays.toString(intNextValues(target)));
    }

    private static int repeatNum(String str) {
        if (str.length() == 1) {
            return 0;
        }
        String prv = str.substring(0, str.length() - 1);
        int lastNum = repeatNum(prv);
        System.out.println(lastNum);
        return str.charAt(str.length() - 1) == prv.charAt(lastNum) ? lastNum + 1 : 0;
    }

    public static int[] intNextValues(String str) {
        int[] res = new int[str.length()];
        res[0] = -1;
        int k = -1;
        for (int i = 1; i < str.length(); i++) {
            while (k > -1 && str.charAt(i) != str.charAt(k + 1)) {
                k = res[k];
            }
            if (str.charAt(k + 1) == str.charAt(i)) {
                k++;
            }
            res[i] = k;
        }
        return res;
    }
}
