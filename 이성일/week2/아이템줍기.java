import java.util.ArrayDeque;

class Solution {

    public static int[][] board = new int[51][51];
    // BFS용 4방향
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

// 테두리 판별용 8방향
    static int[] checkDx = {-1,-1,0,1,1,1,0,-1};
    static int[] checkDy = {0,1,1,1,0,-1,-1,-1};

    static class Point {
        int r;
        int c;
        int moving;
        Point(int r, int c, int moving) {
            this.r = r;
            this.c = c;
            this.moving = moving;
        }
    }
    public static boolean inRange(int n, int x, int y) {
        return ((x >= 0 && x < n) && (y >= 0 && y < n)) ? true : false;
    }

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = Integer.MAX_VALUE;
        // 1. rectangles 기준으로 테두리 제외 모두 색칠하기
        for (int[] rec: rectangle){
            for (int i = (rec[0] + 1); i < rec[2]; i++ ){
                for (int j = (rec[1] + 1); j < rec[3]; j++){
                    board[i][j] = -1;
                }
            }
        }
        // 2. 전체 순회 4방향에 칠한 부분(테두리)있으면 제외, 칠해진 부분 제외, 모든 영역 칠하기
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == -1) continue;
                boolean flag = true;
                for (int d = 0; d < checkDx.length; d++) {
                    int x = i + checkDx[d], y = j + checkDy[d];
                    if (!inRange(board.length, x, y)) continue;
                    if (board[x][y] == -1) {
                        flag = false;
                        break;
                        // 칠하면 안되는 부분 (테두리임)
                    }
                }
                if (flag) board[i][j] = -2;
            }
        }
        // 3. 이후, 시작점에서 4방향 bfs로 안칠해진 부분 기준으로 순회 시작
        ArrayDeque<Point> deq = new ArrayDeque<>();
        deq.add(new Point(characterX, characterY,0));
        board[characterX][characterY] = 1;
        while (!deq.isEmpty()) {
            Point p = deq.poll();
            int r = p.r;
            int c = p.c;
            int move = p.moving; // 시작지점부터 이 포인트까지 몇번 움직였는 지를 나타내는 변수
            if ((r==itemX) && (c==itemY)) {
                // 도착했다면 값 갱신
                answer = Math.min(answer, move);
                break;
            } 
            for (int i = 0; i < dx.length; i++) {
                int x = r + dx[i], y = c + dy[i];
                if (!inRange(board.length, x, y)) continue;
                if (board[x][y] !=  0) continue; // 칠해져 있거나 방문했거나
                // if (board[x][y] == 1) continue; // 방문노드
                deq.add(new Point(x,y,move+1));
                board[x][y] = 1;
            }
        }
            
        
        return answer;

    }
}
