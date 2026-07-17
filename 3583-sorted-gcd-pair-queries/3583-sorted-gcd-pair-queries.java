import java.util.*;

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        long[] cnt = new long[max + 1];

        for (int i = 1; i <= max; i++) {
            long c = 0;
            for (int j = i; j <= max; j += i) {
                c += freq[j];
            }
            cnt[i] = c * (c - 1) / 2;
        }

        for (int i = max; i >= 1; i--) {
            for (int j = i + i; j <= max; j += i) {
                cnt[i] -= cnt[j];
            }
        }

        long[] prefix = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            prefix[i] = prefix[i - 1] + cnt[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long q = queries[i] + 1; 

            int lo = 1, hi = max;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (prefix[mid] >= q)
                    hi = mid;
                else
                    lo = mid + 1;
            }
            ans[i] = lo;
        }

        return ans;
    }
}