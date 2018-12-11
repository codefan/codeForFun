
public class Solution {
    private static final double DIFF = 1E-6F;

    public  boolean judgePoint24(int[] nums) {
        double[] doubleNumbles = new double[4];
        for (int i = 0; i < nums.length; i++) {
            doubleNumbles[i] = (double) nums[i];
        }

        return  _judgePoint24(doubleNumbles, doubleNumbles.length);
    }

    public  boolean _judgePoint24(double[] nums, int n) {
        if (n == 1) {
            return (Math.abs(nums[0] - 24)) < DIFF;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double a = nums[i];
                double b = nums[j];


                //每计算一次，参与运算的数字就少一个。将最后一个数字复制到位置j,可达到缩小（缩短）数组的效果
                nums[j] = nums[n - 1];


                //加法操作 a+b
                nums[i] = a + b;
                if (_judgePoint24(nums, n - 1)) {
                    return true;
                }

                //减法操作 a-b
                nums[i] = a - b;
                if (_judgePoint24(nums, n - 1)) {
                    return true;
                }

                //减法操作 b-a
                nums[i] = b - a;
                if (_judgePoint24(nums, n - 1)) {
                    return true;
                }

                //乘法操作 a*b
                nums[i] = a * b;
                if (_judgePoint24(nums, n - 1)) {
                    return true;
                }

                //除法操作 a/b, 如果除数不为0
                if (b != 0) {
                    nums[i] = a / b;
                    if (_judgePoint24(nums, n - 1)) {
                        return true;
                    }
                }

                //除法操作 b/a , 如果除数不为0
                if (a != 0) {
                    nums[i] = b / a;
                    if (_judgePoint24(nums, n - 1)) {
                        return true;
                    }
                }

                nums[i] = a;
                nums[j] = b;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = { 3, 3, 8, 8 };
        System.out.println(s.judgePoint24(nums));

    }
}

