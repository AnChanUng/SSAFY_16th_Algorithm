import java.util.*;

class Solution {
    static int N;
    static int[][] map;
    static int[] dx = {0,1,-1,0};
    static int[] dy = {1,0,0,-1};
    static int[][][] dist;
    public int solution(int[][] board) {
        this.N = board.length;
        this.map = board;
        
        bfs(0,0);
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++){
            min = Math.min(min, dist[N-1][N-1][i]);
        }
        return min;
    }
    
    static void bfs(int x, int y){
        dist = new int[N][N][4];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
    }
}
        dist[0][0][0] = 0;
        dist[0][0][1] = 0;
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x,y,0});
         q.offer(new int[]{x,y,1});
        
        while(!q.isEmpty()){
            int[] current = q.poll();
            
            for(int dir = 0; dir < 4; dir++){
                int nx = current[0] + dx[dir];
                int ny = current[1] + dy[dir];
                
                
                if(!inRange(nx,ny)) continue;
                if(map[nx][ny] == 1) continue;
                
                int cost = dist[current[0]][current[1]][current[2]] + 100;

                if(current[2] != dir){
                    cost += 500;
                }
                
                if(dist[nx][ny][dir] > cost){
                    dist[nx][ny][dir] = cost;
                    q.offer(new int[]{nx,ny,dir}); //dir은 지금 방향, current[2]는 이전 방향
                }
                
            }
        }
    }
    static boolean inRange(int x, int y){
        return x >=0 && x < N && y >= 0 && y < N;
    }
}
