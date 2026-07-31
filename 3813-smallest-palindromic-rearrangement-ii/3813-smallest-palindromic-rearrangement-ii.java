import java.math.BigInteger;

class Solution {

    public String smallestPalindrome(String s, int k) {

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int[] half = new int[26];
        int odd = -1;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            if ((freq[i] & 1) == 1)
                odd = i;
        }

        int halfLen = s.length() / 2;

        if (countWays(half, halfLen, k).compareTo(BigInteger.valueOf(k)) < 0)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                BigInteger ways = countWays(half, halfLen - pos - 1, k);

                if (ways.compareTo(BigInteger.valueOf(k)) >= 0) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways.intValue();
                    half[c]++;
                }
            }
        }

        String first = left.toString();
        String middle = odd == -1 ? "" : String.valueOf((char) ('a' + odd));
        String second = new StringBuilder(first).reverse().toString();

        return first + middle + second;
    }

    private BigInteger countWays(int[] cnt, int total, int limit) {

        BigInteger ans = BigInteger.ONE;
        int remain = total;

        for (int i = 0; i < 26; i++) {

            int x = cnt[i];

            if (x == 0)
                continue;

            BigInteger ways = BigInteger.ONE;
            
            int r = Math.min(x, remain - x); 
            
            for (int j = 1; j <= r; j++) {
                ways = ways.multiply(BigInteger.valueOf(remain - j + 1));
                ways = ways.divide(BigInteger.valueOf(j));
                
                if (ways.compareTo(BigInteger.valueOf(limit)) > 0) {
                    ways = BigInteger.valueOf(limit + 1L);
                    break;
                }
            }

            ans = ans.multiply(ways);
            if (ans.compareTo(BigInteger.valueOf(limit)) > 0) {
                return BigInteger.valueOf(limit + 1L);
            }
            
            remain -= x;
        }

        return ans;
    }
}