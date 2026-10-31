
class Solution {
    private static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] go = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                go[i][j][0] = -1;
                go[i][j][1] = -1;
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                if (grid[i][j] == 0) {
                    go[i][j][0] = i * n + j;
                    go[i][j][1] = i * n + j;
                } else {
                    if (i + 1 < m) {
                        go[i][j][0] = go[i + 1][j][1];
                    }

                    if (j + 1 < n) {
                        go[i][j][1] = go[i][j + 1][0];
                    }
                }
            }
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int ways = dp[i][j];

                if (ways == 0) continue;

                if (j + 1 < n) {
                    int target = go[i][j + 1][0];

                    if (target != -1) {
                        int x = target / n;
                        int y = target % n;

                        dp[x][y] = (int)(
                            (dp[x][y] + (long) ways) % MOD
                        );
                    }
                }

                if (i + 1 < m) {
                    int target = go[i + 1][j][1];

                    if (target != -1) {
                        int x = target / n;
                        int y = target % n;

                        dp[x][y] = (int)(
                            (dp[x][y] + (long) ways) % MOD
                        );
                    }
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}
