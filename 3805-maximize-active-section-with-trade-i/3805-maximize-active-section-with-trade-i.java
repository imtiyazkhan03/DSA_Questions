class Solution {
    public int maxActiveSectionsAfterTrade(String s) {

        int totalOnes = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') totalOnes++;
        }

        String t = "1" + s + "1";

        ArrayList<Integer> len = new ArrayList<>();
        ArrayList<Character> ch = new ArrayList<>();

        int count = 1;

        for (int i = 1; i < t.length(); i++) {

            if (t.charAt(i) == t.charAt(i - 1)) {
                count++;
            } else {
                ch.add(t.charAt(i - 1));
                len.add(count);
                count = 1;
            }
        }

        ch.add(t.charAt(t.length() - 1));
        len.add(count);

        int gain = 0;

        for (int i = 1; i < ch.size() - 1; i++) {

            if (ch.get(i) == '1'
                    && ch.get(i - 1) == '0'
                    && ch.get(i + 1) == '0') {

                gain = Math.max(gain,
                        len.get(i - 1) + len.get(i + 1));
            }
        }

        return totalOnes + gain;
    }
}