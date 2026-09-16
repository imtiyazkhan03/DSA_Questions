class Solution {

    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;

        int target = 2 * k;
        int limit = n + k - 1;

        long[] dp = new long[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= limit; i++) {
            for (int j = Math.min(i, target); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }

        return (int) dp[target];
    }
}