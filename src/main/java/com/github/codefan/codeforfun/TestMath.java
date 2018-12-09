package com.github.codefan.codeforfun;

import org.apache.commons.lang3.StringUtils;

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
        SearchFormula.makeOperatorArray2(4,
                (data) -> System.out.println(StringUtils.join(data,",")));
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
