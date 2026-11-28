import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] gain = new int[n + 1];

        for (int f = 1; f <= n; f++) {
            int count = 0;

            for (int multiple = f; multiple <= n; multiple += f) {
                count += freq[multiple];
            }

            gain[f] = count - 1;
        }

        Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]));

        long answer = 0;
        long remaining = budget;
        long limit = 2L * minPrice;

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (price >= limit) {
                break;
            }

            int availableBonus = gain[factor];

            if (availableBonus == 0) {
                continue;
            }

            long canBuy = remaining / price;

            if (canBuy == 0) {
                break;
            }

            long take = Math.min((long) availableBonus, canBuy);

            answer += 2L * take;
            remaining -= take * price;
        }

        answer += remaining / minPrice;

        return (int) answer;
    }
}