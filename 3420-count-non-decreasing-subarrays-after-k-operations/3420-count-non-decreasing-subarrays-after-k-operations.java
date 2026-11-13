
class Solution {

    private long getCost(
        int left, int right,
        int[] nums,
        long[] prefix,
        int[][] jump,
        long[][] blockCost,
        int log
    ) {
        int cur = left;
        long total = 0;

        for (int p = log - 1; p >= 0; p--) {
            int nxt = jump[p][cur];

            if (nxt <= right + 1 && nxt > cur) {
                total += blockCost[p][cur];
                cur = nxt;
            }
        }

        if (cur <= right) {
            total += (long) nums[cur] * (right - cur + 1)
                   - (prefix[right + 1] - prefix[cur]);
        }

        return total;
    }

    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int[] nextGreater = new int[n];
        int[] stack = new int[n];
        int top = 0;

        for (int i = n - 1; i >= 0; i--) {

            while (top > 0 &&
                   nums[stack[top - 1]] < nums[i]) {
                top--;
            }

            nextGreater[i] = (top == 0) ? n : stack[top - 1];

            stack[top++] = i;
        }

        int log = 1;

        while ((1 << log) <= n) {
            log++;
        }

        int[][] jump = new int[log][n + 1];
        long[][] blockCost = new long[log][n + 1];

        for (int i = 0; i < n; i++) {

            jump[0][i] = nextGreater[i];

            blockCost[0][i] =
                (long) nums[i] * (nextGreater[i] - i)
                - (prefix[nextGreater[i]] - prefix[i]);
        }

        jump[0][n] = n;

        for (int p = 1; p < log; p++) {

            jump[p][n] = n;

            for (int i = 0; i < n; i++) {

                int mid = jump[p - 1][i];

                jump[p][i] = jump[p - 1][mid];

                blockCost[p][i] =
                    blockCost[p - 1][i]
                    + blockCost[p - 1][mid];
            }
        }

        int left = 0;
        long ans = 0;

        for (int right = 0; right < n; right++) {

            while (left <= right &&
                   getCost(left, right, nums, prefix,
                           jump, blockCost, log) > k) {
                left++;
            }

            ans += right - left + 1;
        }

        return ans;
    }
}
