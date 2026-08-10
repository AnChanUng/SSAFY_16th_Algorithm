/*
전략
- 완전탐색 DFS
- 가능한 모든 경로로 (N-1, N-1)에 도달시키고, 그 때의 비용 구하기
    - 만약 현재 비용이 현재 결과값보다 커지면 프루닝
- dist 배열을 3차원으로 사용해서 (x좌표, y좌표, 방향)을 저장하고 낮은 값으로 갱신
    - 같은 좌표여도 바라보는 방향이 다른 경우 다른 결과를 내기 때문

시행착오
- 2차원 dist 배열을 사용했으나 특정 지점에서는 최소비용이 아니나 
  결과적으로 최소비용이 되는 반례가 있었음
- 백트래킹으로 변경했으나 시간초과 발생함
  프루닝 조건을 여럿 추가해도 시간초과를 피할 수 없음
*/

class Solution {
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, -1, 1};
    static int[][] board;
    static int[][][] dist;
    
    class Car{
        int x;
        int y;
        int dir;
        int cost;
        
        Car(int x, int y, int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
            cost = 0;
        }
    }
    
    public int solution(int[][] b) {
        board = b;
        dist = new int[board.length][board.length][4];
        
        // dist 배열 초기화(x좌표, y좌표, 방향)
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                for(int k = 0; k < 4; k++){
                    dist[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }
        // (0, 0)의 비용을 0으로 초기화(4방향)
        dist[0][0][0] = 0;
        dist[0][0][1] = 0;
        dist[0][0][2] = 0;
        dist[0][0][3] = 0;
        
        dfs(new Car(0, 0, 1)); // 밑 보고 출발
        dfs(new Car(0, 0, 3)); // 오른쪽 보고 출발
        
        // 결과 찾기(목표 지점의 4방향)
        int answer = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++){
            answer = Math.min(answer, dist[board.length - 1][board.length - 1][i]);
        }
        
        return answer;
    }
    
    private void dfs(Car car){
        // 4방향 탐색
        for(int i = 0; i < 4; i++){
            int nx = car.x + dx[i];
            int ny = car.y + dy[i];
            boolean isCurve = (car.dir != i);
            
            if(!inRange(nx, ny)){
                continue;
            }
            
            if(board[nx][ny] == 0){
                Car next = new Car(nx, ny, i);
                // 커브면 (커브500 + 직선도로100)으로 600
                next.cost = (isCurve) ? car.cost + 600 : car.cost + 100;
                
                // 종료 조건
                if(next.x == board.length - 1 && next.y == board.length - 1){
                    dist[next.x][next.y][next.dir] = Math.min(dist[next.x][next.y][next.dir] , next.cost);
                    return;
                }
                // pruning 
                else if(next.cost > dist[next.x][next.y][next.dir]){
                    continue;    
                }
                // 재귀
                else{    
                    dist[next.x][next.y][next.dir] = next.cost;
                    dfs(next);
                }
            }
        }
    }
    
    private boolean inRange(int x, int y){
        return (x >= 0 && x < board.length && y >= 0 && y < board.length);
    }
}