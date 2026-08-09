import java.util.*;
/*
    시작점에서 도착점까지 가는 최소 비용 계산c
    :(0, 0) -> (n-1, n-1)

    알고리즘: 다익스트라, 우선순위유

    비어있는 칸: 0
    벽: 1
    직선도로 개수: 전체칸수에서 -1 (개당 100원)
    코너: 방향을 튼 개수 (개당 500원)
    
    1. 0, 0에서 출발한다
    2. 다익스트라
*/
class Solution {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int[][][] dist;
    static int n;
    static int minTotal;
    static class Node implements Comparable<Node> {
        int x;
        int y;
        int cost;
        int direction;
        
        Node(int x, int y, int cost, int direction) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.direction = direction;
        }
        
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
    public int solution(int[][] board) {
        minTotal = Integer.MAX_VALUE;
        n = board.length;
        
        dijkstra(0, 0, board);        
        
        for(int i=0; i<4; i++) {
            minTotal = Math.min(minTotal, dist[n-1][n-1][i]);
        }
        
        return minTotal;
    }
    static void dijkstra(int x, int y, int[][] board) {
        dist = new int[n][n][4];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) { 
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
            }
        }
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(x, y, 0, -1));
        
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            
            for(int dir=0; dir<4; dir++) {
                int nx = cur.x + dx[dir];
                int ny = cur.y + dy[dir];
                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                if(board[nx][ny] == 1) continue;
                
                int nCost = cur.cost + 100;
                if(cur.direction != -1 && dir != cur.direction) {
                    nCost += 500;
                }
                
                if(nCost < dist[nx][ny][dir]) {
                    dist[nx][ny][dir] = nCost; 
                    pq.offer(new Node(nx, ny, nCost, dir));
                }
            }
        }
    }
}
