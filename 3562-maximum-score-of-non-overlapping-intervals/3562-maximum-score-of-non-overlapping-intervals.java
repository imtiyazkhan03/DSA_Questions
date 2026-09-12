class Solution {

    public int[] maximumWeight(java.util.List<java.util.List<Integer>> intervals) {

        int n = intervals.size();

        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        java.util.Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int[] previous = new int[n];

        for (int i = 0; i < n; i++) {

            int low = 0;
            int high = i - 1;
            int answer = -1;

            while (low <= high) {

                int mid = low + (high - low) / 2;

                if (arr[mid][1] < arr[i][0]) {
                    answer = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            previous[i] = answer + 1;
        }

        long[][] dp = new long[5][n + 1];

        int[][][] path = new int[5][n + 1][];

        for (int count = 0; count <= 4; count++) {
            for (int i = 0; i <= n; i++) {
                dp[count][i] = 0;
                path[count][i] = new int[0];
            }
        }

        for (int count = 1; count <= 4; count++) {

            for (int i = 1; i <= n; i++) {

                int current = i - 1;

                dp[count][i] = dp[count][i - 1];
                path[count][i] = path[count][i - 1];

                int previousIndex = previous[current];

                long candidateScore =
                        arr[current][2] + dp[count - 1][previousIndex];

                int[] candidatePath = addIndex(
                        path[count - 1][previousIndex],
                        arr[current][3]
                );

                if (candidateScore > dp[count][i]) {

                    dp[count][i] = candidateScore;
                    path[count][i] = candidatePath;

                } else if (candidateScore == dp[count][i]
                        && compare(candidatePath, path[count][i]) < 0) {

                    path[count][i] = candidatePath;
                }
            }
        }

        int[] result = new int[0];
        long bestScore = 0;

        for (int count = 1; count <= 4; count++) {

            if (dp[count][n] > bestScore) {

                bestScore = dp[count][n];
                result = path[count][n];

            } else if (dp[count][n] == bestScore
                    && compare(path[count][n], result) < 0) {

                result = path[count][n];
            }
        }

        return result;
    }

    private int[] addIndex(int[] oldPath, int index) {

        int[] result = new int[oldPath.length + 1];

        for (int i = 0; i < oldPath.length; i++) {
            result[i] = oldPath[i];
        }

        result[oldPath.length] = index;

        java.util.Arrays.sort(result);

        return result;
    }

    private int compare(int[] a, int[] b) {

        int length = Math.min(a.length, b.length);

        for (int i = 0; i < length; i++) {

            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }
}