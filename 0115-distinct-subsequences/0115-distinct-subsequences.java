class Solution {

    long[][] dp;

    public int numDistinct(String s, String t) {

        dp = new long[s.length()][t.length()];

        for (long[] row : dp) {
            Arrays.fill(row, -1);
        }

        return (int) solve(0, 0, s, t);
    }

    private long solve(int i, int j,
                       String s, String t) {

        if (j == t.length()) {
            return 1;
        }

        if (i == s.length()) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        long ans;

        if (s.charAt(i) == t.charAt(j)) {

            ans =
                solve(i + 1, j + 1, s, t) +
                solve(i + 1, j, s, t);

        } else {

            ans = solve(i + 1, j, s, t);
        }

        return dp[i][j] = ans;
    }
}