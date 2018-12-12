
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

    public static void main(String[] args) {
        Solution s = new Solution();
        //int[] nums = { 3, 3, 8, 8 };
        System.out.println(s.allocateCandy(5,6));

    }
}
// 18851684897584
// 1397281501935165
// 6365767297450757748
// 6009637891325159524
// 1837153897788780084
// 8115943777403887392
// 6650043942411187488