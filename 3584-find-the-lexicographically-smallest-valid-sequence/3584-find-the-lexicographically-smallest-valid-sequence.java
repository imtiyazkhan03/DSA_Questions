class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        int[] suffix = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                j--;
            }

            suffix[i] = m - 1 - j;
        }

        int[] ans = new int[m];

        j = 0;
        boolean changed = false;

        for (int i = 0; i < n && j < m; i++) {

            // Normal matching
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }

            // Use the one allowed mismatch
            else if (!changed) {

                int remaining = m - j - 1;

                if (i + 1 <= n && suffix[i + 1] >= remaining) {
                    ans[j] = i;
                    j++;
                    changed = true;
                }
            }
        }

        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}