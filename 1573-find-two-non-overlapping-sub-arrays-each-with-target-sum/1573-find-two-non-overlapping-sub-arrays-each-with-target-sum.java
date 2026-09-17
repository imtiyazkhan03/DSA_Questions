class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = 1_000_000_000;
        int[] best = new int[n];

        int left = 0;
        int sum = 0;
        int minLength = INF;
        int answer = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target && left <= right) {
                sum -= arr[left];
                left++;
            }

            if (sum == target) {
                int currentLength = right - left + 1;

                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(
                        answer,
                        currentLength + best[left - 1]
                    );
                }

                minLength = Math.min(minLength, currentLength);
            }

            best[right] = minLength;
        }

        return answer == INF ? -1 : answer;
    }
}