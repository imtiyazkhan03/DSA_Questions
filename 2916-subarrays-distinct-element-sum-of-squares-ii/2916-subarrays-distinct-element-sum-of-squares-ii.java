
class Solution {
    private static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegmentTree st = new SegmentTree(n);
        int[] last = new int[100001];

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int prev = last[x] - 1;

            st.update(prev + 1, i, 1);
            ans = (ans + st.querySumSquares()) % MOD;

            last[x] = i + 1;
        }

        return (int) ans;
    }

    private static class SegmentTree {
        int n;
        long[] sum, sumSq, lazy;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            sumSq = new long[4 * n];
            lazy = new long[4 * n];
        }

        void update(int l, int r, long val) {
            if (l > r) return;
            update(1, 0, n - 1, l, r, val);
        }

        void update(int node, int start, int end,
                    int l, int r, long val) {
            if (r < start || end < l) return;

            if (l <= start && end <= r) {
                apply(node, start, end, val);
                return;
            }

            push(node, start, end);

            int mid = (start + end) / 2;
            update(node * 2, start, mid, l, r, val);
            update(node * 2 + 1, mid + 1, end, l, r, val);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            sumSq[node] = (sumSq[node * 2] + sumSq[node * 2 + 1]) % MOD;
        }

        void apply(int node, int start, int end, long val) {
            long len = end - start + 1L;

            sumSq[node] = (sumSq[node]
                    + 2L * val % MOD * sum[node] % MOD
                    + len % MOD * val % MOD * val % MOD) % MOD;

            sum[node] = (sum[node] + len % MOD * val % MOD) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        void push(int node, int start, int end) {
            if (lazy[node] == 0 || start == end) return;

            int mid = (start + end) / 2;
            apply(node * 2, start, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, end, lazy[node]);

            lazy[node] = 0;
        }

        long querySumSquares() {
            return sumSq[1];
        }
    }
}
