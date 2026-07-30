import java.util.*;
import java.io.*;

//연결요소 찾는 문제이므로 dfs or bfs로 둘다 풀이가가

public class SWEA5643 {
	static int N;
	static int M;
	static ArrayList<Integer>[] tallGraph;
	static ArrayList<Integer>[] shortGraph;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		
		for(int tc = 1; tc <= T; tc++) {
			
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());
			
			tallGraph = new ArrayList[N + 1];
			shortGraph = new ArrayList[N + 1];
			
			for(int i = 1; i <= N; i++) {
				tallGraph[i] = new ArrayList<>();
				shortGraph[i] = new ArrayList<>();
			}
			
			for(int i = 0; i < M; i++) {
				
				st = new StringTokenizer(br.readLine());
				
				int shorter = Integer.parseInt(st.nextToken());
				int taller = Integer.parseInt(st.nextToken());
				
				// shorter보다 키가 큰 학생
				tallGraph[shorter].add(taller);
				
				// taller보다 키가 작은 학생
				shortGraph[taller].add(shorter);
			}
			
			int result = 0;
			
			for(int i = 1; i <= N; i++) {
				
				boolean[] visited = new boolean[N + 1];
				int tallerCnt = dfs(i, tallGraph, visited);
				
				visited = new boolean[N + 1];
				int shorterCnt = dfs(i, shortGraph, visited);
				
				if(tallerCnt + shorterCnt == N - 1) {
					result++;
				}
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(result);
			System.out.println(sb);
			  
		}
		
		
	}
	
	static int dfs(int current, ArrayList<Integer>[] currentGraph,
			boolean[] visited) {
		
		visited[current] = true;
		int count = 0;
		
		for(Integer next : currentGraph[current]) {
			
			if(!visited[next]) {
				count++;
				count += dfs(next, currentGraph, visited);
			}
		}
		
		return count;
	}
}