import java.util.*;

class Solution {

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    long sumLeft = 0, sumRight = 0;
    int sizeLeft = 0, sizeRight = 0;

    void addLeft(int val) {
        left.merge(val, 1, Integer::sum);
        sumLeft += val;
        sizeLeft++;
    }

    void addRight(int val) {
        right.merge(val, 1, Integer::sum);
        sumRight += val;
        sizeRight++;
    }

    void removeLeft(int val) {
        remove(left, val);
        sumLeft -= val;
        sizeLeft--;
    }

    void removeRight(int val) {
        remove(right, val);
        sumRight -= val;
        sizeRight--;
    }

    void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }

    void balance(int x) {
        int target = (x + 1) / 2;

        while (sizeLeft > target) {
            int val = left.lastKey();
            removeLeft(val);
            addRight(val);
        }

        while (sizeLeft < target) {
            int val = right.firstKey();
            removeRight(val);
            addLeft(val);
        }
    }

    long getCost() {
        int median = left.lastKey();

        return (long) median * sizeLeft - sumLeft
             + sumRight - (long) median * sizeRight;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        left.clear();
        right.clear();
        sumLeft = sumRight = 0;
        sizeLeft = sizeRight = 0;

        for (int i = 0; i < n; i++) {

            int val = nums[i];

            if (left.isEmpty() || val <= left.lastKey()) {
                addLeft(val);
            } else {
                addRight(val);
            }

            if (i >= x) {
                int old = nums[i - x];

                if (left.containsKey(old)) {
                    removeLeft(old);
                } else {
                    removeRight(old);
                }
            }

            balance(Math.min(i + 1, x));

            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {

                dp[j][i] = dp[j][i - 1];

                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}