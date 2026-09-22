class Solution {

    static class Node {
        long[] pref;
        int product;

        Node(int k) {
            pref = new long[k];
        }
    }

    int k;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Update nums[index]
            update(1, 0, n - 1, index, value);

            // Get information for nums[start ... n-1]
            Node res = query(1, 0, n - 1, start, n - 1);

            result[i] = (int) res.pref[x];
        }

        return result;
    }

    // Create a leaf node
    private Node leaf(int value) {

        Node node = new Node(k);

        int rem = value % k;

        node.product = rem;

        // The only prefix is the element itself
        node.pref[rem] = 1;

        return node;
    }

    // Build segment tree
    private void build(int node, int l, int r, int[] nums) {

        if (l == r) {
            tree[node] = leaf(nums[l]);
            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    // Merge two segments:
    //
    // A = left segment
    // B = right segment
    //
    // Prefixes of A remain unchanged.
    // Prefixes extending into B have:
    //
    // product(A) * prefix(B)
    //
    private Node merge(Node a, Node b) {

        Node res = new Node(k);

        res.product = (a.product * b.product) % k;

        // Prefixes completely inside A
        for (int r = 0; r < k; r++) {
            res.pref[r] = a.pref[r];
        }

        // Prefixes = complete A + prefix of B
        for (int r = 0; r < k; r++) {

            int newRem = (a.product * r) % k;

            res.pref[newRem] += b.pref[r];
        }

        return res;
    }

    // Point update
    private void update(int node, int l, int r,
                        int index, int value) {

        if (l == r) {
            tree[node] = leaf(value);
            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    // Range query
    private Node query(int node, int l, int r,
                       int ql, int qr) {

        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }
}