import java.util.*;

class Solution {

    static int[][] map = new int[102][102];
    static int[][] dist = new int[102][102];

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public int solution(int[][] rectangle,
                        int characterX, int characterY,
                        int itemX, int itemY) {

        for (int[] r : rectangle) {

            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;

            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    map[x][y] = 1;
                }
            }
        }

        // 2. 사각형 내부를 0으로 지움, 테두리만 1로 남김
        for (int[] r : rectangle) {

            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;

            for (int x = x1 + 1; x < x2; x++) {
                for (int y = y1 + 1; y < y2; y++) {
                    map[x][y] = 0;
                }
            }
        }

    
        characterX *= 2;
        characterY *= 2;
        itemX *= 2;
        itemY *= 2;

        return bfs(characterX, characterY, itemX, itemY) / 2;
    }

    static int bfs(int startX, int startY, int itemX, int itemY) {

        ArrayDeque<int[]> q = new ArrayDeque<>();

        q.offer(new int[]{startX, startY});
        dist[startX][startY] = 1;

        while (!q.isEmpty()) {

            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            if (x == itemX && y == itemY) {
                return dist[x][y] - 1;
            }

            for (int d = 0; d < 4; d++) {

                int nx = x + dx[d];
                int ny = y + dy[d];

                // 테두리가 아니면 못 감
                if (map[nx][ny] != 1) continue;

                // 이미 방문했다면 못 감
                if (dist[nx][ny] != 0) continue;

                dist[nx][ny] = dist[x][y] + 1;
                q.offer(new int[]{nx, ny});
            }
        }

        return -1;
    }
}
