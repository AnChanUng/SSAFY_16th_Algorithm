import java.util.*;
import java.io.*;

// board의 각 칸. compareTo 오버라이딩을 위해 Comparable 구현.
class Cell implements Comparable<Cell>{
	int time;
	int x;
	int y;
	
	public Cell(int time, int x, int y){
		this.time = time;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int compareTo(Cell c) {
		return Integer.compare(this.time, c.time);
	}
}

public class SWEA1249 {
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static Cell[][] board;
	static boolean[][] visited;
	static int[][] dist; // 각 칸의 최단거리 결과값 저장
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			board = new Cell[N][N];
			visited = new boolean[N][N];
			dist = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				String line = br.readLine();
				for(int j = 0; j < N; j++) {
					board[i][j] = new Cell(line.charAt(j) - '0', i, j);
					dist[i][j] = Integer.MAX_VALUE;
				}
			}
			
			// 시작점(0, 0)에서 다익스트라 시작
			dijkstra(0, 0);
			
			// 종료 지점 (N - 1, N - 1)의 dist값 출력
			sb.append("#").append(t).append(" ").append(dist[N - 1][N - 1]).append("\n");
		}	
		
		System.out.print(sb);
	}
	
	private static void dijkstra(int x, int y) {
		PriorityQueue<Cell> pq = new PriorityQueue<>();
		
		// 시작점 방문처리
		pq.offer(new Cell(0, x, y));
		visited[x][y] = true;
		dist[x][y] = 0;
		
		while(!pq.isEmpty()) {
			// pq에서 Cell을 하나 꺼내서 4방향 델타 탐색
			Cell cell = pq.poll();
			int currTime = cell.time;
			
			for(int i = 0; i < 4; i++) {
				int nx = cell.x + dx[i];
				int ny = cell.y + dy[i];
			
				if(!inRange(nx, ny)) {
					continue;
				}
				
				Cell next = board[nx][ny];
				int nextTime = currTime + next.time;
				
				// 다음 Cell의 dist  VS  현재 Cell까지의 거리 + 다음 Cell의 time을 비교
				// 후자가 작으면 후자가 더 짧은 거리이므로 방문처리
				if(nextTime < dist[nx][ny]) {
					pq.offer(new Cell(nextTime, nx, ny));
					visited[nx][ny] = true;
					dist[nx][ny] = nextTime;
				}
			}
		}
	}
	
	// board 범위 내 여부 리턴
	private static boolean inRange(int x, int y) {
		return x >= 0 && x < board.length && y >= 0 && y < board.length;
	}
}
