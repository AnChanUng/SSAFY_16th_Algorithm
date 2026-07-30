package com.ssafy.swb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA1251{
	// 1. 입력 요소 Tc, 좌표 개수, x좌표 들, y좌표들, 상수 E
	// 2. kruskal
		// 2-1 노드별 간선 정보 계산, 2차원 배열에 저장 [v,e, cost]
		// 2-2 cost 기준 정렬
		// 2-3 가장 작은 cost부터 union find 진횅하기
			// 2-3-1 union 시 작은 트리를 큰 트리에 병합하여 depth 낮추기, find 횟수를 인자로 관리하여 횟수 비교
		// 2-4 union count값이 노드개수에 도달하면 탐색 종료
	public static double[][] graph;
	public static int[] parent;
	public static double ans = 0.0;
	
	public static int[] find(double d, int depth) {
		if (parent[(int) d] == (int) d) {
			return new int[] {(int) d, depth};
		} 
		return find(parent[(int) d], depth++);
	}
	
	public static void union(int[] a, int[] b) {
		if (a[1] < b[1]) {
			parent[a[0]] = b[0];
		} else {
			parent[b[0]] = a[0];
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int tc = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= tc; t++) {
			int n = Integer.parseInt(br.readLine());
			int[] xs = new int[n], ys = new int[n];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				xs[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				ys[i] = Integer.parseInt(st.nextToken());
			}
			double E = Double.parseDouble(br.readLine());
			
			
			graph = new double[(n*n - n)/2][3];
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
			
			int cnt = 0;
			for (int i = 0; i < n; i++) {
				int x = xs[i], y = ys[i];
				for (int j = i+1; j < n; j++) {
					int cx = xs[j], cy = ys[j];
					graph[cnt][0] = i;
					graph[cnt][1] = j;
					graph[cnt][2] = E * (Math.pow(x-cx, 2) + Math.pow(y - cy, 2));
					cnt++;
				}
			}
			Arrays.sort(graph, (o1,o2) -> Double.compare(o1[2], o2[2]));
			
			int unionCnt = 1;
			for (double[] d : graph) {
				if (unionCnt == n) break;
				int[] x = find(d[0],0), y = find(d[1],0);
				if (x[0] != y[0]) {
					union(x,y);
					ans += d[2];
					unionCnt++;
				}
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(t).append(" ").append(Math.round(ans));
			System.out.println(sb);
			ans = 0.0;
		}
		
	}
	
	
	
}
