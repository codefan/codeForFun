package com.github.codefan.codeforfun;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TestMath {

    static int optCount=0;

    static void countOptZhuhe(Object[] l){
        optCount ++;
    }

    public static void main(String arg[]) {
        int sortTimes [] = {5,4,3,2};
        int len = sortTimes.length;
        int it [] = new int [len];
        for(int i=0; i<len; i++) {
            it[i] = 0;
        }
        int st = 0;
        while(sortTimes[0] > it[0]){
            for(int i=0; i<len; i++) {
                System.out.print(it[i]+1);
            }
            System.out.println();
            st ++;
            int i=len - 1;
            it[i] = it[i] + 1;
            while(i>0 && it[i] >= sortTimes[i]){
                it[i] = 0;
                i--;
                it[i] = it[i] + 1;
            }
        }
        System.out.println(st);
        //SearchFormula.makeOperatorArray2(4,
        //        (data) -> System.out.println(StringUtils.join(data,",")));
    }

    public static void main3(String arg[]) {

        List<Integer> data = new ArrayList<>(10);

        for(int n = 2; n<10; n++) {
            String[] ops = new String[n - 1];
            for (int i = 0; i < n; i++) {
                data.add(i);
                if (i < n - 1) {
                    ops[i] = "+";
                }
            }
            SearchFormula.makeFormulaAndCalc(data, ops, TestMath::countOptZhuhe);
            System.out.print( ( Math.pow(3,n-2) + 1 )/2);
            System.out.print("  ");
            System.out.println(optCount);
            optCount = 0;
        }
    }

    //https://baike.baidu.com/item/142857/1922511?fr=aladdin
    public static final int peculiarNum = 142857;
    public static void main2(String arg[]) {
        TreeSet<Integer> integerTreeSet = new TreeSet<>();
        integerTreeSet.contains(5);
        System.out.println(new BigDecimal(2.123123));
        System.out.println(new BigDecimal("2.123123"));
    }
}
