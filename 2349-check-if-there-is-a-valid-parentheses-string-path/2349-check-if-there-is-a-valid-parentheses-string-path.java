class Solution {
    public boolean hasValidPath(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int len = m + n - 1;

        if ((len & 1) == 1) {
            return false;
        }

        boolean[][][] dp = new boolean[m][n][len + 1];

        if (grid[0][0] == ')') {
            return false;
        }

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance <= len; balance++) {

                    if (grid[i][j] == '(') {

                        if (balance > 0) {

                            if (i > 0 &&
                                dp[i - 1][j][balance - 1]) {
                                dp[i][j][balance] = true;
                            }

                            if (j > 0 &&
                                dp[i][j - 1][balance - 1]) {
                                dp[i][j][balance] = true;
                            }
                        }

                    } else {

                        if (balance < len) {

                            if (i > 0 &&
                                dp[i - 1][j][balance + 1]) {
                                dp[i][j][balance] = true;
                            }

                            if (j > 0 &&
                                dp[i][j - 1][balance + 1]) {
                                dp[i][j][balance] = true;
                            }
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}