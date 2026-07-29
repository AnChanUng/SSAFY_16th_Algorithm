import java.util.*;
import java.lang.*;
import java.io.*;
/*
    출발지(S)에서 도착지(G)로 이동하는데 복구 시간이 가장 짧은 경로 구하기
    -깊이가 3이면 복구에 드는 시간3
    -0인 곳은 복구작업 불필요

    알고리즘: 가중치 BFS
    시간: 20초 O(n^2)

    1. 입력
    1.1 테스트케이스 수 입력받기
    1.2 지도의 크기 입력 받기
    1.3 지도 입력받기 (0100)
    2. 다익스트라
*/
class SWEA1249 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int[][] board;
    static int[][] dist;
    static int n;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=t; tc++) {
            sb.append("#").append(tc).append(" ");
            n = Integer.parseInt(br.readLine());

            board = new int[n][n];
            for(int i=0; i<n; i++) { // 0100
                String str = br.readLine();
                for(int j=0; j<str.length(); j++) {
                    board[i][j] = str.charAt(j) - '0';
                }
            }
            dijkstra(0, 0);
            
            sb.append(dist[n-1][n-1]).append("\n");
        }
        System.out.print(sb.toString());
    }
    
    static void dijkstra(int x, int y) {
        dist = new int[n][n];
        for(int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        
        dist[x][y] = 0;
        pq.add(new int[]{x, y, 0});
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cx = cur[0];
            int cy = cur[1];
            int cost = cur[2];
            if(cost > dist[cx][cy]) continue;

            for(int dir=0; dir<4; dir++) {
                int nx = cx + dx[dir];
                int ny = cy + dy[dir];
                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                int nCost = cost + board[nx][ny];
                if(nCost < dist[nx][ny]) {
                    dist[nx][ny] = nCost;
                    pq.add(new int[]{nx, ny, nCost});
                }
            }
        }
    }
}