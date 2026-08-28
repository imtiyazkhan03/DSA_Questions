class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int oddCount = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                oddCount++;
                middle = (char) ('a' + i);
            }
        }

        if (oddCount > 1) {
            return "";
        }

        int halfLen = n / 2;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = count[i] / 2;
        }

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            boolean found = false;

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0) {
                    continue;
                }

                half[c]--;
                left.append((char) ('a' + c));

                if (canMakeGreater(left, half, middle, target)) {
                    found = true;
                    break;
                }

                left.deleteCharAt(left.length() - 1);
                half[c]++;
            }

            if (!found) {
                return "";
            }
        }

        String leftPart = left.toString();

        StringBuilder ans = new StringBuilder();

        ans.append(leftPart);

        if (n % 2 == 1) {
            ans.append(middle);
        }

        ans.append(new StringBuilder(leftPart).reverse());

        String result = ans.toString();

        return result.compareTo(target) > 0 ? result : "";
    }


    private boolean canMakeGreater(
            StringBuilder prefix,
            int[] remaining,
            char middle,
            String target) {


        StringBuilder left = new StringBuilder(prefix);
        for (int c = 25; c >= 0; c--) {

            for (int j = 0; j < remaining[c]; j++) {
                left.append((char) ('a' + c));
            }
        }

        String leftPart = left.toString();

        StringBuilder palindrome = new StringBuilder();

        palindrome.append(leftPart);

        if (target.length() % 2 == 1) {
            palindrome.append(middle);
        }

        palindrome.append(new StringBuilder(leftPart).reverse());

        return palindrome.toString().compareTo(target) > 0;
    }
}