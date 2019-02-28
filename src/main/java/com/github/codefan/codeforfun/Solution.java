package com.github.codefan.codeforfun;

import java.util.Arrays;

public class Solution {
    private static final double DIFF = 1E-6F;

    public  boolean judgePoint24(int[] nums) {
        double[] doubleNumbles = new double[4];
        for (int i = 0; i < nums.length; i++) {
            doubleNumbles[i] = (double) nums[i];
        }

        return  _judgePoint(doubleNumbles, doubleNumbles.length, 24);
    }

    public  boolean _judgePoint(double[] nums, int n, int sum) {
        if (n == 1) {
            return (Math.abs(nums[0] - sum)) < DIFF;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double a = nums[i];
                double b = nums[j];
                //每计算一次，参与运算的数字就少一个。将最后一个数字复制到位置j,可达到缩小（缩短）数组的效果
                nums[j] = nums[n - 1];
                //加法操作 a+b
                nums[i] = a + b;
                if (_judgePoint(nums, n - 1, sum)) {
                    return true;
                }
                //减法操作 a-b
                nums[i] = a - b;
                if (_judgePoint(nums, n - 1, sum)) {
                    return true;
                }
                //减法操作 b-a
                nums[i] = b - a;
                if (_judgePoint(nums, n - 1, sum)) {
                    return true;
                }
                //乘法操作 a*b
                nums[i] = a * b;
                if (_judgePoint(nums, n - 1, sum)) {
                    return true;
                }
                //除法操作 a/b, 如果除数不为0
                // 如果有一个为0 和上面的 * 乘法结果一样，也没有必要计算
                if (b != 0 && a != 0) {
                    nums[i] = a / b;
                    if (_judgePoint(nums, n - 1, sum)) {
                        return true;
                    }
                    nums[i] = b / a;
                    if (_judgePoint(nums, n - 1, sum)) {
                        return true;
                    }
                }
                // 恢复数组
                nums[i] = a;
                nums[j] = b;
            }
        }
        return false;
    }

    public long allocateCandy(int kids, int candies){
        if(kids<=1){
            return 1;
        }
        if(candies==1){
            return kids;
        }
        long temp[] = new long[kids];
        for(int i=0; i<kids; i++){
            temp[i] = (long)(i+1);
        }
        long res = temp[kids-1];
        for(int i=1; i<candies; i++){
            res = 1;
            for(int j = 1; j<kids; j++){
                res +=  temp[j];
                temp[j] = res;
            }
        }
        return res;
    }

    public int atMostNGivenDigitSet(String[] D, int N) {
        // 找到最大的数
        String s = String.valueOf(N);
        int [] bigData = new int[s.length()];
        for(int i=0; i<s.length(); i++){
            bigData[i] = D.length-1;
        }
        int bigLen = s.length();
        boolean findBig = false;
        for(int i=0; i<s.length(); i++){
            boolean thisC = true;
            for(int j=D.length-1; j>=0; j--) {
                if (D[j].charAt(0) < s.charAt(i)) {
                    bigData[i] = j;
                    findBig = true;
                    break;
                }
                if (D[j].charAt(0) == s.charAt(i)) {
                    bigData[i] = j;
                    thisC = false;
                    break;
                }
            }

            if(findBig){
                break;
            }

            if(thisC){
                bigData[i] = D.length-1;
                // 借位
                boolean b = false;
                for(int j = i-1; j>=0; j-- ){
                    if(bigData[j]>0){
                        bigData[j] = bigData[j] -1;
                        b = true;
                        break;
                    }
                    if(bigData[j]==0){
                        bigData[j] = D.length-1;
                    }
                }
                if(!b) {
                    bigLen --;
                }
                break;
            }
        }
        int s1=0;
        int sp =  bigLen < s.length() ? 1:0;
        for(int i=0; i<bigLen; i++){
            s1 = s1*D.length +(bigData[sp+i]+1);
        }
        return s1;
    }

    public String largestNumber(int[] nums) {
        if(nums==null || nums.length==0){
            return "0";
        }
        int l = nums.length;
        if(l==0){
            return Integer.toString(nums[0]);
        }
        String[] sl = new String[l];
        for(int i=0;i<l;i++){
            sl[i] = Integer.toString(nums[i]);
        }

        Arrays.sort(sl, (a, b)->{
            int n = a.length();
            int m = b.length();
            for(int i=0;i<n*m;i++){
                char bc = b.charAt(i % m);
                char ac = a.charAt(i % n);
                if(bc>ac){
                    return 1;
                }
                if(ac>bc){
                    return -1;
                }
            }
            return 0;
        });
        StringBuilder res = new StringBuilder();
        for(int i=0;i<l;i++){
            res.append(sl[i]);
        }
        String rets = res.toString();
        if(rets.charAt(0)=='0'){
            return "0";
        }
        return rets;
    }


    public boolean isMatch(String s, String p) {
        if(s==null || p==null)
            return false;
        int nLV = s.length();
        int nLT = p.length();
        int i =0,j=0,ss=-1,sp=-1;
        while(i<nLV){
            if(j < nLT && ( (p.charAt(j) != '*' && s.charAt(i) == p.charAt(j)) ||
                    p.charAt(j) == '?')){
                i++;j++;
            }else if(j < nLT && p.charAt(j) == '*') {
                sp = j;
                j++;
                ss = i;
            }else if(sp != -1){
                j = sp+1;
                ss ++;
                i = ss;
            }else
                return false;
        }
        while(j<nLT && p.charAt(j)=='*'){
           j++;
        }
        return (i==nLV && j==nLT);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isMatch("adfadcadcebdfadsadceb","*adc*adceb"));
        System.out.println(s.isMatch("a","*a*"));
        System.out.println(s.isMatch("ab","*?b*"));
    }
}
