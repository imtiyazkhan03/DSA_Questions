
class Solution {
    public int numOfSubarrays(int[] arr) {

        int MOD = 1000000007;

        int even = 1;
        int odd = 0;

        int prefixSum = 0;
        int ans = 0;

        for (int num : arr) {

            prefixSum += num;

            if (prefixSum % 2 == 0) {
                even++;
                ans = (ans + odd) % MOD;
            } else {
                odd++;
                ans = (ans + even) % MOD;
            }
        }

        return ans;
    }
}
