package com.github.codefan.codeforfun;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Calculation24 {

    /**
     * 非递归的排列
     * @param listSouce 可 排序的 列表
     * @param comparable 比较函数
     * @param consumer 消费排序结果
     * @param <T> 泛型
     */
    public static <T> void permutation(List<T> listSouce ,
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


    public static void main(String arg[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("请在一行中输入用空格隔开的4在1和13之间的整数，退出请输入exit：");
            String s = br.readLine().trim();
            if(StringUtils.isBlank(s)){
                continue;
            }
            if(StringUtils.equalsIgnoreCase("exit",s)){
                break;
            }

            String[] nums = s.split(" ");
            List<Integer> alist = new ArrayList<>(4);
            for (String num : nums) {
                if (SearchFormula.isNumber(num)) {
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
            Calculation24.permutation(
                    alist, Integer::compare,
                    (data) -> SearchFormula.searchFormulaAndCalc(
                            data, (reversePolish) ->
                                    SearchFormula.filterFormula(24, reversePolish,
                                            SearchFormula::transPolish )));

            SearchFormula.showResult();
        }
    }
}

