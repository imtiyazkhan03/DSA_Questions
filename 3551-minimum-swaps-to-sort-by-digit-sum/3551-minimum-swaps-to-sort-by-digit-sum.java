
import java.util.Arrays;

class Solution {

    private int digitSum(int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = digitSum(nums[i]);
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int[] target = new int[n];
        for (int i = 0; i < n; i++) {
            target[i] = arr[i][0];
        }

        java.util.Map<Integer, Integer> indexMap =
                new java.util.HashMap<>();

        for (int i = 0; i < n; i++) {
            indexMap.put(nums[i], i);
        }

        int[] permutation = new int[n];

        for (int i = 0; i < n; i++) {
            permutation[i] = indexMap.get(target[i]);
        }

        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] || permutation[i] == i) {
                continue;
            }

            int cycleSize = 0;
            int j = i;

            while (!visited[j]) {
                visited[j] = true;
                j = permutation[j];
                cycleSize++;
            }

            swaps += cycleSize - 1;
        }

        return swaps;
    }
}
