class Solution {
    public int reverseDegree(String s) {
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            int alphabetPosition = s.charAt(i) - 'a' + 1;
            int reversePosition = 27 - alphabetPosition;

            int stringPosition = i + 1;

            ans += reversePosition * stringPosition;
        }

        return ans;
    }
}