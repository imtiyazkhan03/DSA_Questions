class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1000000007;

        int[] dp = new int[26];

        for (char c : s.toCharArray()) {
            int index = c - 'a';

            int total = 1;

            for (int i = 0; i < 26; i++) {
                total = (total + dp[i]) % MOD;
            }

            dp[index] = total;
        }

        int ans = 0;

        for (int x : dp) {
            ans = (ans + x) % MOD;
        }

        return ans;
    }
}