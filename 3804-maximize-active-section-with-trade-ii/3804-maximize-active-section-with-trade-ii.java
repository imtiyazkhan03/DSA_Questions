import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        
        int[] starts = new int[n];
        int[] ends = new int[n];
        int K = 0;
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
                if (i == 0 || s.charAt(i - 1) == '0') {
                    starts[K] = i;
                    ends[K] = i;
                    K++;
                } else {
                    ends[K - 1] = i;
                }
            }
        }
        
        int[] G = new int[K];
        for (int k = 0; k < K; k++) {
            int prevEndPlus1 = (k == 0) ? 0 : (ends[k - 1] + 1);
            int nextStartMinus1 = (k == K - 1) ? (n - 1) : (starts[k + 1] - 1);
            G[k] = (starts[k] - prevEndPlus1) + (nextStartMinus1 - ends[k]);
        }
        
        int logK = K > 0 ? (32 - Integer.numberOfLeadingZeros(K)) : 0;
        int[][] st = new int[K][logK];
        for (int i = 0; i < K; i++) {
            st[i][0] = G[i];
        }
        for (int j = 1; (1 << j) <= K; j++) {
            for (int i = 0; i + (1 << j) <= K; i++) {
                st[i][j] = Math.max(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
        
        int qLen = queries.length;
        List<Integer> ans = new ArrayList<>(qLen);
        
        for (int q = 0; q < qLen; q++) {
            int L = queries[q][0];
            int R = queries[q][1];
            
            int low = 0, high = K - 1, idx_first = K;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (starts[mid] > L) {
                    idx_first = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            
            low = 0; high = K - 1; int idx_last = -1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (ends[mid] < R) {
                    idx_last = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            
            int maxGain = 0;
            if (idx_first <= idx_last) {
                if (idx_first == idx_last) {
                    int k = idx_first;
                    int prevEndPlus1 = (k == 0) ? 0 : (ends[k - 1] + 1);
                    int nextStartMinus1 = (k == K - 1) ? (n - 1) : (starts[k + 1] - 1);
                    maxGain = (starts[k] - Math.max(L, prevEndPlus1)) + (Math.min(R, nextStartMinus1) - ends[k]);
                } else {
                    int k1 = idx_first;
                    int prevEndPlus1_k1 = (k1 == 0) ? 0 : (ends[k1 - 1] + 1);
                    int nextStartMinus1_k1 = (k1 == K - 1) ? (n - 1) : (starts[k1 + 1] - 1);
                    int gain1 = (starts[k1] - Math.max(L, prevEndPlus1_k1)) + (nextStartMinus1_k1 - ends[k1]);
                    
                    int k2 = idx_last;
                    int prevEndPlus1_k2 = (k2 == 0) ? 0 : (ends[k2 - 1] + 1);
                    int nextStartMinus1_k2 = (k2 == K - 1) ? (n - 1) : (starts[k2 + 1] - 1);
                    int gain2 = (starts[k2] - prevEndPlus1_k2) + (Math.min(R, nextStartMinus1_k2) - ends[k2]);
                    
                    int gain3 = 0;
                    if (idx_first + 1 <= idx_last - 1) {
                      
                        int rmqL = idx_first + 1;
                        int rmqR = idx_last - 1;
                        int len = rmqR - rmqL + 1;
                        int j = 31 - Integer.numberOfLeadingZeros(len);
                        gain3 = Math.max(st[rmqL][j], st[rmqR - (1 << j) + 1][j]);
                    }
                    maxGain = Math.max(gain1, Math.max(gain2, gain3));
                }
            }
            ans.add(totalOnes + maxGain);
        }
        return ans;
    }
}