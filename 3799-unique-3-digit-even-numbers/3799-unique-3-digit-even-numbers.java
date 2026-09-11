class Solution {
    public int totalNumbers(int[] digits) {
        int[] freq = new int[10];

        for (int digit : digits) {
            freq[digit]++;
        }

        int answer = 0;

        for (int num = 100; num <= 999; num++) {

            if (num % 2 != 0) {
                continue;
            }

            int a = num / 100;          
            int b = (num / 10) % 10;    
            int c = num % 10;        

            freq[a]--;

            if (freq[a] < 0) {
                freq[a]++;
                continue;
            }

            freq[b]--;

            if (freq[b] < 0) {
                freq[a]++;
                freq[b]++;
                continue;
            }

            freq[c]--;

            if (freq[c] >= 0) {
                answer++;
            }

            freq[a]++;
            freq[b]++;
            freq[c]++;
        }

        return answer;
    }
}