
import java.util.*;

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }

        boolean increasing = true;
        for (int i = 0; i < n; i++) {
            if (nums[(zero + i) % n] != i) {
                increasing = false;
                break;
            }
        }

        boolean decreasing = true;
        for (int i = 0; i < n; i++) {
            if (nums[(zero - i + n) % n] != i) {
                decreasing = false;
                break;
            }
        }

        if (!increasing && !decreasing) {
            return -1;
        }

        int target;
        if (increasing) {
            target = zero;
        } else {
            target = n + (n - 1 - zero);
        }

        int[] dist = new int[2 * n];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        dist[0] = 0;

        while (!queue.isEmpty()) {
            int state = queue.poll();

            if (state == target) {
                return dist[state];
            }

            int type = state / n;
            int shift = state % n;

            int rotated = type * n + (shift + 1) % n;
            if (dist[rotated] == -1) {
                dist[rotated] = dist[state] + 1;
                queue.offer(rotated);
            }

            int reversed = (1 - type) * n + (n - shift) % n;
            if (dist[reversed] == -1) {
                dist[reversed] = dist[state] + 1;
                queue.offer(reversed);
            }
        }

        return -1;
    }
}
