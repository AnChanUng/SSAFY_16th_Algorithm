import java.util.*;
import java.io.*;


/*
 * 시행착오: 문제를 보고 오토에버에서 본 크루스칼 알고리즘인 것 같아서 관련 내용 및 기본 코드를 공부 후 풀이 진행
 * Edge 클래스에서 compareTo를 this.cost - e.cost로 작성했다가 반환형이 맞지 않아 Long.compare(this.cost, e.cost);로 변경
 * 이후에 세율을 간선 비용 구할떄 넣어놨는데 리턴타입이 맞지 않아 에러 발생 
 * -> 간선 비용에서는 계산하지 않고 마지막에 구한 값에 * 세율을 곱하고 Math.round() 적용
 */
public class SWEA1251 {
	static int N;
	static ArrayList<Edge> edgeList;
	static int[] parent;
	
	static class Edge implements Comparable<Edge>{
		
		int from;
		int to;
		long cost; // 간선의 비용
		
		Edge(int from, int to, long cost){
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Edge e) {
			return Long.compare(this.cost, e.cost);
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		for(int tc = 1; tc <= T; tc++) {
			
			edgeList = new ArrayList<>();
			
			N = Integer.parseInt(br.readLine());
			
			int[][] node = new int[N + 1][2];
			
			st = new StringTokenizer(br.readLine());
			
			for(int i = 1; i <= N; i++) {
				node[i][0] = Integer.parseInt(st.nextToken()); // i번 노드의 x좌표
			}
			
			st = new StringTokenizer(br.readLine());
			
			for(int i = 1; i <= N; i++) {
				node[i][1] = Integer.parseInt(st.nextToken()); // i번 노드의 y좌표
			}
			
			double rate = Double.parseDouble(br.readLine());
			
			for(int i = 1; i < N; i++) {
				for(int j = i + 1; j <= N; j++) {
					calculateCost(node, i, j);
				}
			}
			
			Collections.sort(edgeList);
			
			parent = new int[N + 1];
			
			for(int i = 1; i <= N; i++) {
				parent[i] = i; // 부모를 자기 자신으로 초기화
			}
			
			long totalDistance = kruskal(parent);
			
			long result = Math.round(totalDistance * rate);
			
			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}
		
		System.out.print(sb);
	}
	
	static void calculateCost(int[][] node, int i, int j) {
		
		long distanceX = (long)node[i][0] - node[j][0];
		long distanceY = (long)node[i][1] - node[j][1];
		
		long cost = distanceX * distanceX + distanceY * distanceY;
		
		edgeList.add(new Edge(i, j, cost));
	}
	
	static long kruskal(int[] parent) {
		
		long cost = 0;
		int edgeCnt = 0;
		
		for(int i = 0; i < edgeList.size(); i++) {
			
			Edge edge = edgeList.get(i);
			
			int from = edge.from;
			int to = edge.to;
			long weight = edge.cost;
			
			if(find(parent, from) != find(parent, to)) {
				
				union(parent, from, to);
				
				cost += weight;
				edgeCnt++;
				
				if(edgeCnt == N - 1) {
					break;
				}
			}
		}
		
		return cost;
	}
	
	static int find(int[] parent, int x) {
		
		if(parent[x] == x) {
			return x;
		}
		
		return parent[x] = find(parent, parent[x]);
	}
	
	static void union(int[] parent, int x, int y) {
		
		x = find(parent, x);
		y = find(parent, y);
		
		if(x < y) {
			parent[y] = x;
		}else {
			parent[x] = y;
		}
	}
}