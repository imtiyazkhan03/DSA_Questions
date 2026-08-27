class Solution {

    public String lexGreaterPermutation(String s, String target) {

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < target.length(); i++) {

            int t = target.charAt(i) - 'a';

            if (freq[t] > 0) {

                prefix.append(target.charAt(i));
                freq[t]--;

            } else {

                int greater = -1;

                for (int c = t + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        greater = c;
                        break;
                    }
                }

                if (greater != -1) {

                    prefix.append((char) ('a' + greater));
                    freq[greater]--;

                    for (int c = 0; c < 26; c++) {
                        while (freq[c] > 0) {
                            prefix.append((char) ('a' + c));
                            freq[c]--;
                        }
                    }

                    return prefix.toString();
                }

                // Cannot continue at this position.
                break;
            }
        }


        for (int i = prefix.length() - 1; i >= 0; i--) {

            int current = prefix.charAt(i) - 'a';

            freq[current]++;

            for (int c = current + 1; c < 26; c++) {

                if (freq[c] > 0) {

                    StringBuilder result = new StringBuilder();

                    result.append(prefix.substring(0, i));

                    result.append((char) ('a' + c));
                    freq[c]--;

                    for (int x = 0; x < 26; x++) {
                        while (freq[x] > 0) {
                            result.append((char) ('a' + x));
                            freq[x]--;
                        }
                    }

                    return result.toString();
                }
            }
        }

        return "";
    }
}