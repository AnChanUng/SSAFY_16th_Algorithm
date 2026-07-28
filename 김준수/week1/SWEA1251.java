/*
전략

- 최소 신장 트리 - 크루스칼
    - 최소 비용의 간선부터 그리디하게 병합 시도
- 모든 섬에 대해 교차하여 간선을 만든다
- 간선을 최소 비용으로 정렬한다
- 간선을 앞에서부터 순회하며 간선의 두 정점을 병합
    - 두 섬의 루트 노드가 같으면 병합 시 사이클이 만들어질 수 있으므로 스킵
    - 이 사이클 찾기 및 병합 과정을 유니온 파인드로 진행
    - 현재 사용한 간선의 개수가 N - 1개면 조기 종료
        - N개의 노드를 연결하는 최소한의 간선은 N - 1개이기 때문

시행착오

- 크루스칼 알고리즘의 개념이 부족하여 크루스칼 알고리즘 기초부터 학습한 후 단계별로 풀이법을 정리함
- 크루스칼 알고리즘의 구현을 위해서는 유니온 파인드를 사용해야 하는데 
  개념이 부족하여 유니온 파인드 자료구조의 기초부터 학습한 후 단계별로 구현 방법을 정리함
 */

import java.util.*;
import java.io.*;


public class SWEA1251 {
	static int[] parent; // 유니온 파인드 Make-set
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			List<List<Integer>> islands = new ArrayList<>();
			List<Edge> edges = new ArrayList<>();
			parent = new int[N + 1];
			
			// X 좌표 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			int[] xList = new int[N];
			for(int i = 0; i < N; i++) {
				xList[i] = Integer.parseInt(st.nextToken());
			}
			
			// Y 좌표 입력
			st = new StringTokenizer(br.readLine());
			int[] yList = new int[N];
			for(int i = 0; i < N; i++) {
				yList[i] = Integer.parseInt(st.nextToken());
			}
			
			// 섬 초기화
			for(int i = 0; i < N; i++) {
				islands.add(new ArrayList<>());
				islands.get(i).add(xList[i]);
				islands.get(i).add(yList[i]);
				
				parent[i] = i; // Make-set 초기화
			}
			
			// 세금 입력
			double tax = Double.parseDouble(br.readLine());
			
			// 간선 추가(모든 섬에 대해 교차해서)
			for(int i = 0; i < N; i++) {
				for(int j = i + 1; j < N; j++) {
					double length = Math.pow(islands.get(i).get(0) - islands.get(j).get(0), 2);
					length += Math.pow(islands.get(i).get(1) - islands.get(j).get(1), 2);
					length = Math.sqrt(length);
					
					edges.add(new Edge(i, j, length * length * tax));
				}
			}
			
			// 최소 비용으로 간선 정렬
			Collections.sort(edges);
			
			double totalCost = 0;
			int edgeCnt = 0;
			
			// 간선 순회
			for(Edge edge : edges) {
				int rootU = find(edge.u);
				int rootV = find(edge.v);
				
				// 두 섬의 루트 노드가 같지 않다 = 유니온 해도 사이클이 안만들어진다
				if(rootU != rootV) {
					union(edge.u, edge.v);
					totalCost += edge.cost;
					edgeCnt++;
				}
				
				// 간선이 N - 1개면 모든 섬이 연결되었으므로 조기 종료
				if(edgeCnt == N - 1) {
					break;
				}
			}
			
			// 결과에 소수 첫째 자리에서 반올림 
			sb.append("#").append(t).append(" ").append(Math.round(totalCost)).append("\n");
		}
		System.out.print(sb);
	}
	
	// 유니온 파인드 find
	static int find(int x) {
		if(parent[x] == x) {
			return x;
		}
		
		return parent[x] = find(parent[x]); // 경로 압축
	}
	
	// 유니온 파인드 union
	static void union(int u, int v) {
		int rootU = find(u);
		int rootV = find(v);
		
		if(rootU != rootV) {
			parent[rootU] = rootV;
		}
	}
}

class Edge implements Comparable<Edge>{
	public int u;
	public int v;
	public double cost;
	
	public Edge(int u, int v, double cost) {
		this.u = u;
		this.v = v;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Edge e) {
		return Double.compare(this.cost, e.cost);
	}
}
