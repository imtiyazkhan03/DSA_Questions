
class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();
        int INF = 1000000000;

        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {

            for (int start = 0; start + len <= n; start++) {

                int best = INF;

                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;
                    int groupSize = len / d;

                    for (int r = 0; r < d; r++) {

                        int left = 0;
                        int right = groupSize - 1;

                        while (left < right) {

                            char c1 = s.charAt(start + r + left * d);
                            char c2 = s.charAt(start + r + right * d);

                            if (c1 != c2) {
                                changes++;
                            }

                            left++;
                            right--;
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[start][start + len - 1] = best;
            }
        }


        int[][] dp = new int[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = INF;
            }
        }

        dp[0][0] = 0;

        for (int t = 1; t <= k; t++) {

            for (int i = 2 * t; i <= n; i++) {

                for (int j = 2 * (t - 1); j <= i - 2; j++) {

                    if (dp[t - 1][j] == INF) {
                        continue;
                    }

                    dp[t][i] = Math.min(
                        dp[t][i],
                        dp[t - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
