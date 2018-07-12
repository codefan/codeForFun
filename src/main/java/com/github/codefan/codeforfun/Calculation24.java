package com.github.codefan.codeforfun;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;

public class Calculation24 {

    private static Map<String, List<String>> foundReslutions = new HashMap<>(50);
    /**
     * 非递归的排列组合
     * @param listSouce 可 排序的 列表
     * @param comparable 比较函数
     * @param consumer 消费排序结果
     * @param <T> 泛型
     */
    public static <T> void  combination(List<T> listSouce ,
                                        Comparator<? super T> comparable,
                                        Consumer<List<T>> consumer){
        if(listSouce.size()<2){
            consumer.accept(listSouce);
            return;
        }
        listSouce.sort(comparable);
        int len = listSouce.size();
        List<Integer> comPos = new ArrayList<>(len);
        List<Boolean> usedItem = new ArrayList<>(len);
        List<T> comRes = new ArrayList<>(len);
        for(int i=0;i<len;i++){
            comPos.add(-1);
            usedItem.add(false);
            comRes.add(null);
        }
        comPos.set(0,0);
        usedItem.set(0, true);
        int sortIndex = 0;
        while(sortIndex >=0 ){
            comRes.set(sortIndex, listSouce.get( comPos.get(sortIndex)));
            if( sortIndex == len - 2){ // 如果获得一个排序
                for(int i=0; i< len; i++){
                    if(!usedItem.get(i)){// 将最后一个未使用的添加到排列的最后
                        comRes.set( sortIndex +1, listSouce.get(i));
                        break;
                    }
                }
                consumer.accept(comRes);
                while(sortIndex >=0 ) {
                    //下一个
                    int prePos = comPos.get(sortIndex);
                    usedItem.set(prePos, false);
                    //当前pos ++ （步进）
                    while (comPos.get(sortIndex) + 1  < len &&
                            ( usedItem.get(comPos.get(sortIndex) + 1) ||
                                    comparable.compare( listSouce.get(prePos),
                                            listSouce.get(comPos.get(sortIndex) + 1))== 0 )) {
                        comPos.set(sortIndex, comPos.get(sortIndex) + 1);
                    }
                    comPos.set(sortIndex, comPos.get(sortIndex) + 1);
                    // 如果已经到上线，继续回退
                    if (comPos.get(sortIndex)  < len ) {
                        //重新计算下个列表
                        usedItem.set(comPos.get(sortIndex), true);
                        comRes.set( sortIndex, listSouce.get(comPos.get(sortIndex)));
                        break;
                    }else{ // 回退
                        sortIndex--;
                        //comPos.set(sortIndex, comPos.get(sortIndex) + 1);
                    }
                }
            } else { // 下一个
                for(int i=0; i< len; i++){
                    if(!usedItem.get(i)){
                        comPos.set(sortIndex + 1,i);
                        usedItem.set(i,true);
                        break;
                    }
                }
                sortIndex++;
            }
        }
    }

    //逆波兰式
    private static float calcReversePolishRepresentation(Object[] reversePolish) {
        float[] stack = new float[4];
        int j = 0;
        for (int i = 0; i < 7; i++) {
            if (reversePolish[i] instanceof Integer) {
                stack[j] =  ((Integer) reversePolish[i]).floatValue();
                j++;
            } else {
                switch ((String) reversePolish[i]) {
                    case "+":
                        stack[j - 2] = stack[j - 2] + stack[j - 1];
                        break;
                    case "-":
                        stack[j - 2] = stack[j - 2] - stack[j - 1];
                        break;
                    case "*":
                        stack[j - 2] = stack[j - 2] * stack[j - 1];
                        break;
                    case "/":
                        if (stack[j - 1] == 0) {
                            return -1;
                        }
                        stack[j - 2] = stack[j - 2] / stack[j - 1];
                        break;
                }
                j--;
            }
        }
        return stack[0];
    }

    // 算24点 并将结果的逆波兰式转换为 四则运算表达式
    @SuppressWarnings("unchecked")
    private static void checkResult(Object[] reversePolish){
        if( Math.abs(calcReversePolishRepresentation(reversePolish) - 24) < 0.0001f  ){
            Pair<String, String>[] stack = new Pair[4];
            int j = 0;
            for (int i = 0; i < 7; i++) {
                if (reversePolish[i] instanceof Integer) {
                    stack[j] = new ImmutablePair<>("O",String.valueOf(reversePolish[i]));
                    j++;
                } else {
                    switch ((String) reversePolish[i]) {
                        case "+":
                            stack[j - 2] =
                                    new ImmutablePair<>("+",
                                            stack[j - 2].getRight()+"+"+stack[j - 1].getRight()
                                    );
                            break;
                        case "-":
                             stack[j - 2] =
                                    new ImmutablePair<>("-",
                                            stack[j - 2].getRight()+
                                                    ( StringUtils.equalsAny( stack[j - 1].getLeft(), "-","+")
                                                           ? "-("+stack[j - 1].getRight()+")"
                                                            : "-"+stack[j - 1].getRight())
                                    );
                            break;
                        case "*":
                            stack[j - 2] =
                                    stack[j - 2] =
                                            new ImmutablePair<>("*",
                                                    ( StringUtils.equalsAny( stack[j - 2].getLeft(), "-","+")
                                                            ? "("+stack[j - 2].getRight()+")"
                                                            : stack[j - 2].getRight())
                                                    +
                                                            ( StringUtils.equalsAny( stack[j - 1].getLeft(), "-","+")
                                                                    ? "*("+stack[j - 1].getRight()+")"
                                                                    : "*"+stack[j - 1].getRight())
                                            );
                            break;
                        case "/":
                            stack[j - 2] =
                                    new ImmutablePair<>("/",
                                            ( StringUtils.equalsAny( stack[j - 2].getLeft(), "-","+")
                                                    ? "("+stack[j - 2].getRight()+")"
                                                    : stack[j - 2].getRight())
                                                    +
                                                    ( StringUtils.equalsAny( stack[j - 1].getLeft(), "-","+","*","/")
                                                            ? "/("+stack[j - 1].getRight()+")"
                                                            : "/"+stack[j - 1].getRight())
                                    );
                            break;
                    }
                    j--;
                }
            }
            String rb = StringUtils.join(reversePolish, " ");
            List<String> rbs = foundReslutions.get(stack[0].getRight());
            if(rbs==null){
                rbs = new ArrayList<>();
                rbs.add(rb);
                foundReslutions.put(stack[0].getRight(), rbs);
            }else{
                rbs.add(rb);
            }
        }
    }

    //将 数字和操作排序
    //这部分代码写的比较笨拙，应该可以更优美一点
    private static void calc24Point(List<Integer> rList){
        String[] opts = {"+","-","*","/"};
        Object [] stack = new Object[7];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                for(int k=0;k<4;k++){
                    // a b + c + d +  (((a+b)+c)+d)
                    stack[0] = rList.get(0);
                    stack[1] = rList.get(1);
                    stack[2] = opts[i];
                    stack[3] = rList.get(2);
                    stack[4] = opts[j];
                    stack[5] = rList.get(3);
                    stack[6] = opts[k];
                    checkResult(stack);
                    // a b + c d + +  ((a+b)+(c+d))
                    stack[3] = rList.get(2);
                    stack[4] = rList.get(3);
                    stack[5] = opts[j];
                    checkResult(stack);
                    // a b c + d + +  (a+((b+c)+d))
                    stack[2] = rList.get(2);
                    stack[3] = opts[i];
                    stack[4] = rList.get(3);
                    checkResult(stack);
                    // a b c + + d +  ((a+(b+c))+d)
                    stack[3] = opts[i];
                    stack[4] = opts[j];
                    stack[5] = rList.get(3);
                    checkResult(stack);
                    // a b c d + + +  (a+(b+(c+d)))
                    stack[3] = rList.get(3);
                    stack[4] = opts[i];
                    stack[5] = opts[j];
                    checkResult(stack);
                }
            }
        }
    }

    //判断输入的是否为数值
    private static boolean isNumber(String strNum) {
        if(StringUtils.isBlank(strNum)){
            return false;
        }
        for(int i=0; i<strNum.length(); i++){
            if(strNum.charAt(i)<'0' || strNum.charAt(i)>'9'){
                return false;
            }
        }
        return true;
    }

    public static void main(String arg[]) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("请在一行中输入用空格隔开的4在1和10之间的整数，退出请输入exit：");
            String s = br.readLine().trim();
            if(StringUtils.isBlank(s)){
                continue;
            }
            if(StringUtils.equalsIgnoreCase("exit",s)){
                break;
            }
            foundReslutions.clear();
            String[] nums = s.split(" ");
            List<Integer> alist = new ArrayList<>(4);
            for (String num : nums) {
                if (Calculation24.isNumber(num)) {
                    //这边没有判断范围
                    alist.add(Integer.valueOf(num));
                    if (alist.size() == 4) {
                        break;
                    }
                }
            }
            if( alist.size() < 4){
                continue;
            }
            Calculation24.combination(
                    alist, Integer::compare, Calculation24::calc24Point
            );
            //展示结果
            int sc=0;
            for(Map.Entry<String,List<String>> ent : foundReslutions.entrySet()){
                sc ++;
                System.out.print((sc<10?" "+sc+ ": ": sc + ": ")+ ent.getKey());
                for(int i= ent.getKey().length(); i<16; i++ ){
                    System.out.print(" ");
                }
                System.out.println(ent.getValue().get(0));
                for(int i=1; i<ent.getValue().size();i++){
                    System.out.print("                    ");
                    System.out.println(ent.getValue().get(i));
                }
            }
            System.out.println("一共中找到 " + sc + " 个不同方案。");
        }
    }
}

