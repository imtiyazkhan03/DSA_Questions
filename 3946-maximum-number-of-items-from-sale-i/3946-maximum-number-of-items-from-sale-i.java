
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int NEG = -1_000_000_000;

        int[] dp = new int[budget + 1];
        java.util.Arrays.fill(dp, NEG);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            int freeCount = 0;

            for (int j = 0; j < n; j++) {
                if (j != i && items[j][0] % factor == 0) {
                    freeCount++;
                }
            }

            int[] buy = new int[budget + 1];
            java.util.Arrays.fill(buy, NEG);

            for (int c = price; c <= budget; c++) {

                int firstBuy = dp[c - price] + 1 + freeCount;

                int extraBuy = buy[c - price] + 1;

                buy[c] = Math.max(firstBuy, extraBuy);
            }

            for (int c = 0; c <= budget; c++) {
                dp[c] = Math.max(dp[c], buy[c]);
            }
        }

        int answer = 0;

        for (int c = 0; c <= budget; c++) {
            answer = Math.max(answer, dp[c]);
        }

        return answer;
    }
}
