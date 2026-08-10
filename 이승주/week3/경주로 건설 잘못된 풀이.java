import java.util.*;

/*
    처음에 구현하였을 떄 예제 3번에서 에러가 남, 오른쪽 방향으로만 offer를 해서 밑으로 가는 경우에 +500원이 책정이 됨, 이를 막기 위해 밑의 방향도 offer를 해줌
    처음엔 dist dist[nx][ny] > cost 방식으로 하여 (1,1)에서 700으로 값이 같아서 큐에 들어가지 않아 최적의 경로를 찾지 못함 -> dist업데이트 조건을 >=로 바꾸어 예제 그림과 같이 최적의 경로가 되게함. 이렇게 하니깐 테케 3개 틀리고 테케를 알 수가 없어서  gpt한테 힌트받음..
*/

class Solution {
    static int N;
    static int[][] map;
    static int[] dx = {0,1,-1,0};
    static int[] dy = {1,0,0,-1};
    static int[][] dist;
    public int solution(int[][] board) {
        this.N = board.length;
        this.map = board;
        
        bfs(0,0);
        
        
        return dist[N-1][N-1];
    }
    
    static void bfs(int x, int y){
        dist = new int[N][N];
        
        for(int i = 0; i < N; i++){
        Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x,y,0});
        q.offer(new int[]{x,y,1});
        
        while(!q.isEmpty()){
            int[] current = q.poll();
            
            for(int dir = current[2]; dir < current[2]+4; dir++){
                int nx = current[0] + dx[dir%4];
                int ny = current[1] + dy[dir%4];
                
                
                if(!inRange(nx,ny)) continue;
                if(map[nx][ny] == 1) continue;
                
                int cost = dist[current[0]][current[1]] + 100;

                if(current[2] != dir){
                    cost += 500;
                }
                
                if(dist[nx][ny] >= cost){
                    dist[nx][ny] = cost;
                    q.offer(new int[]{nx,ny,dir}); //dir은 지금 방향, current[2]는 이전 방향
                }
                
            }
        }
    }
    static boolean inRange(int x, int y){
        return x >=0 && x < N && y >= 0 && y < N;
    }
}
