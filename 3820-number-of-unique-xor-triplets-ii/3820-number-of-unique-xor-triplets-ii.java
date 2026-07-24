class Solution {
    public int uniqueXorTriplets(int[] nums) {
        boolean[] pair = new boolean[2048];
        boolean[] ans = new boolean[2048];

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                pair[nums[i] ^ nums[j]] = true;
            }
        }
        for (int num : nums) {
            for (int x = 0; x < 2048; x++) {
                if (pair[x]) {
                    ans[num ^ x] = true;
                }
            }
        }
        int count = 0;
        for (boolean b : ans) {
            if (b) count++;
        }

        return count;
    }
}