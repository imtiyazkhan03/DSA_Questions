class Solution {

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int sx = 0;
        int sy = 0;
        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sx = i;
                    sy = j;
                }

                if (ch == 'L') {
                    litterCount++;
                }
            }
        }

        if (litterCount == 0) {
            return 0;
        }

        int[][] litterId = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                litterId[i][j] = -1;
            }
        }

        int id = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (classroom[i].charAt(j) == 'L') {
                    litterId[i][j] = id++;
                }
            }
        }

        int fullMask = (1 << litterCount) - 1;

        int[][][] bestEnergy =
                new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int mask = 0; mask <= fullMask; mask++) {
                    bestEnergy[i][j][mask] = -1;
                }
            }
        }

        java.util.ArrayDeque<int[]> queue =
                new java.util.ArrayDeque<>();

        queue.offer(new int[] {
            sx, sy, 0, energy, 0
        });

        bestEnergy[sx][sy][0] = energy;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {

            int[] state = queue.poll();

            int x = state[0];
            int y = state[1];
            int mask = state[2];
            int currentEnergy = state[3];
            int steps = state[4];

            for (int d = 0; d < 4; d++) {

                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }

                if (classroom[nx].charAt(ny) == 'X') {
                    continue;
                }

                if (currentEnergy == 0) {
                    continue;
                }

                int newEnergy = currentEnergy - 1;

                int newMask = mask;

                char cell = classroom[nx].charAt(ny);

                if (cell == 'L') {

                    int bit = litterId[nx][ny];

                    newMask |= (1 << bit);
                }

                if (cell == 'R') {
                    newEnergy = energy;
                }

                if (newMask == fullMask) {
                    return steps + 1;
                }
                if (newEnergy <= bestEnergy[nx][ny][newMask]) {
                    continue;
                }

                bestEnergy[nx][ny][newMask] = newEnergy;

                queue.offer(new int[] {
                    nx,
                    ny,
                    newMask,
                    newEnergy,
                    steps + 1
                });
            }
        }

        return -1;
    }
}