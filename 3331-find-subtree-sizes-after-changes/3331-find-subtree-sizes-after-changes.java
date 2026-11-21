
class Solution {
    int[] answer;
    int[] newParent;
    int[] last;
    int[] parent;
    String s;
    List<Integer>[] graph;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        this.parent = parent;
        this.s = s;

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            graph[parent[i]].add(i);
        }

        newParent = new int[n];
        last = new int[26];

        java.util.Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            newParent[i] = parent[i];
        }

        dfsFindParent(0);

        List<Integer>[] finalGraph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            finalGraph[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            finalGraph[newParent[i]].add(i);
        }

        answer = new int[n];
        dfsSize(0, finalGraph);

        return answer;
    }

    private void dfsFindParent(int node) {
        int c = s.charAt(node) - 'a';

        int previous = last[c];

        last[c] = node;

        for (int child : graph[node]) {
            int ch = s.charAt(child) - 'a';

            if (last[ch] != -1) {
                newParent[child] = last[ch];
            }

            dfsFindParent(child);
        }

        last[c] = previous;
    }

    private int dfsSize(int node, List<Integer>[] finalGraph) {
        int size = 1;

        for (int child : finalGraph[node]) {
            size += dfsSize(child, finalGraph);
        }

        answer[node] = size;
        return size;
    }
}
