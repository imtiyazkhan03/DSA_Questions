class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        int[] freq = new int[1001];

        for (int i = 0; i <= n - k; i++) {

            boolean[] seen = new boolean[1001];

            for (int j = i; j < i + k; j++) {
                int x = nums[j];

                if (!seen[x]) {
                    freq[x]++;
                    seen[x] = true;
                }
            }
        }

        int ans = -1;

        for (int x = 0; x <= 1000; x++) {
            if (freq[x] == 1) {
                ans = x;
            }
        }

        return ans;
    }
}