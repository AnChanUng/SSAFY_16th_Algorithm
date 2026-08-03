import java.util.*;
import java.io.*;
public class SWEA5656 {
	static int[][] board;
	static int min;
	static int H;
	static int W;
	static int N;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		
		for(int tc = 1; tc <= T; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			board = new int[H][W];
			
			for(int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < W; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			min = Integer.MAX_VALUE;
			
			dfs(0,board);
			
			System.out.println("#" + tc + " " + min);
			
		}
		
		
	}
	// 구슬을 떨어뜨릴 열을 선택하는 완전탐색
	static void dfs(int depth, int[][] board) {
		
		if(depth == N) {
			int temp = countBrick(board);
			
			min = Math.min(min, temp);
			return;
			
		}
		
		for(int i = 0; i < W; i++) {
			
			int[][] copy = copyMap(board);
			searchBrick(i, copy,depth);
		}
		
	}
	
	
	
	
	//구슬을 떨어뜨릴 열을완전탐색
	static void searchBrick(int col, int[][] board,int depth) {
		
		for(int i = 0; i < H; i++) {
			if(board[i][col] !=0) {

				breakBrick(i,col,board); // 부숴진 맵, 이제 떨궈야함
				dropBrick(board);
				
				dfs(depth+1, board);
				return; //해당 메서드는 첫번째 벽돌만 찾고 종료 시켜야함.
				
			}
		}
		
		dfs(depth+1, board); //!!!!!!!!!해당열에 벽돌이 없는 경우
	}
	
	
	static void breakBrick(int x, int y, int[][] board) {

	    ArrayDeque<int[]> q = new ArrayDeque<>();
	    q.offer(new int[] {x, y});

	    while (!q.isEmpty()) {

	        int[] current = q.poll();
	        int a = current[0];
	        int b = current[1];

	        if (board[a][b] == 0) {
	            continue;
	        }

	        int power = board[a][b];
	        board[a][b] = 0;

	        for (int dir = 0; dir < 4; dir++) {

	            for (int distance = 1; distance < power; distance++) {

	                int nx = a + dx[dir] * distance;
	                int ny = b + dy[dir] * distance;

	                if (!inRange(nx, ny)) {
	                    continue;
	                }

	                if (board[nx][ny] != 0) {
	                    q.offer(new int[] {nx, ny});
	                }
	            }
	        }
	    }
	}
	
	
static void dropBrick(int[][] map) {
	
	for(int col = 0; col < W; col++) {
		
		int writeRow = H-1;
		
		for(int row = H-1; row >= 0; row--) {
			if(map[row][col] != 0) {
				
				int value = map[row][col];
				map[row][col] = 0; //0이 아니면 현재 위치 0으로 하고 값 적어야하는 곳으로 이동시킴
				
				map[writeRow][col] = value;
				writeRow--;
			}
		}
	}
}
	
	static int countBrick(int[][] map){
		int cnt = 0;
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				if(map[i][j] != 0) {
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	
	//맵 깊은 복사
	static int[][] copyMap(int[][] map){
		int[][] copy = new int[H][W];

		for(int i = 0; i < H; i++) {
			copy[i] = map[i].clone();
		}
		
		return copy;
		
	}
	
	static boolean inRange(int x, int y) {
		return x>=0 && x  < H &&  y >= 0 && y < W;
	}

}
