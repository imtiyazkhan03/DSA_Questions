
class Solution {

    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            long product = 1;
            long gcdVal = 0;
            long lcmVal = 1;

            for (int j = i; j < n; j++) {
                int num = nums[j];

                product *= num;

                gcdVal = gcd(gcdVal, num);

                lcmVal = (lcmVal / gcd(lcmVal, num)) * num;

                if (product == gcdVal * lcmVal) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
