import java.util.*;
import java.io.*;

//처음에는 bfs 백트래킹으로 푸는 문제인줄 알고 시도하였지만 시간 초과, 이후 각 위치에 비용이 존재한다는 것을 깨닫고 다익스트라 진행
//우선순위큐이기때문에 (N-1,N-1)을 도칙했을때 최단 경로임
public class SWEA1249 {
	static int[][] board;
	static int N;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	
	static class Node implements Comparable<Node>{
		int x;
		int y;
		int cost;
		
		Node(int x, int y, int cost){
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			
			N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				String line = br.readLine();
				for(int j = 0; j < N; j++) {
					
					board[i][j] = line.charAt(j) - '0';
					
				}
			}
			
			int minTime = dijkstra(new Node(0,0,0));
			
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(minTime);
			System.out.println(sb);
			
		}
		
		
		
		
	}
	
	static int dijkstra(Node node) {
		
		int[][] dist = new int[N][N];
		int max_value = Integer.MAX_VALUE;
		
		for(int i = 0; i < N; i++) {
			Arrays.fill(dist[i], max_value);
		}
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[0][0] = 0;
		
		pq.offer(new Node(0,0,0));
		
		while(!pq.isEmpty()) {
			
			Node current = pq.poll();
			
		
			
			if(current.x == N-1 && current.y == N-1) {
				return dist[N-1][N-1]; //목적지에 도착하는 순간 최소를 보장
			}
			
			for(int dir = 0; dir < 4; dir++) {
				int nx = current.x + dx[dir];
				int ny = current.y + dy[dir];
				
				
				if(!inRange(nx,ny)) continue;
				
				if(current.cost + board[nx][ny] < dist[nx][ny]) {
					dist[nx][ny] = current.cost + board[nx][ny];
					pq.offer(new Node(nx,ny,dist[nx][ny]));
				}
			}
			
			
			
		}
		return dist[N-1][N-1];
		
		
		
	}
	
	static boolean inRange(int x, int y) {
		return x>= 0 && x < N && y >= 0 && y < N;
	}
}
