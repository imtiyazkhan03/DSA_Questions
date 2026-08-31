class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int first = -1;
        int prev = -1;
        int minDist = Integer.MAX_VALUE;

        ListNode prevNode = head;
        ListNode curr = head.next;

        int index = 1;

        while (curr.next != null) {

            if ((curr.val > prevNode.val && curr.val > curr.next.val) ||
                (curr.val < prevNode.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = index;
                }

                if (prev != -1) {
                    minDist = Math.min(minDist, index - prev);
                }

                prev = index;
            }

            prevNode = curr;
            curr = curr.next;
            index++;
        }

        if (first == -1 || first == prev) {
            return new int[]{-1, -1};
        }

        int maxDist = prev - first;

        return new int[]{minDist, maxDist};
    }
}