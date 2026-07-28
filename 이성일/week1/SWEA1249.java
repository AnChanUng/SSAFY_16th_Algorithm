package com.ssafy.swb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

class Node implements Comparable<Node>{
	int r;
	int c;
	int cost;
	
	Node(int r, int c, int cost){
		this.r = r;
		this.c = c;
		this.cost = cost;
	}
	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.cost, o.cost);
	}
	
}

public class SWEA1249 {
	
	public static int[] dx = {-1, 0, 1, 0};
	public static int[] dy = {0, 1, 0, -1};
	
	public static boolean inRange(int i,int j,int n) {
		return (i >= 0 && i < n && j >= 0 && j < n);
	}
	
	public static int dijkstra(PriorityQueue<Node> pq,
			int[][] dist, boolean[][] visited, int[][] board) {
		
		while (!pq.isEmpty()) {
			
			Node v = pq.poll();
			
			int r = v.r, c = v.c;
			visited[r][c] = true;
			
			for (int d = 0; d < 4; d++) {
				int ar = r + dx[d]; int ac = c + dy[d];
				if (inRange(ar, ac, dist.length) && !visited[ar][ac]) {
					if (dist[ar][ac] > v.cost + board[ar][ac]) {	
						dist[ar][ac] = v.cost + board[ar][ac];
						pq.offer(new Node(ar,ac,dist[ar][ac]));
					}
				}
			}
			
			
		}
		return dist[dist.length - 1][dist.length - 1];
	}
	
	public static void main(String[] args) throws Exception {
		// T 입력받고 테케별로 size, board 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());
		
		for (int i = 1; i <= t; i++) {
			int n = Integer.parseInt(br.readLine());
			int[][] board = new int[n][n];
			
			boolean[][] visited = new boolean[n][n];
			int[][] dist = new int[n][n];
			for (int[] arr: dist) {
				Arrays.fill(arr, Integer.MAX_VALUE);
			}
			dist[0][0] = 0;
			
			for (int r = 0; r < n; r++) {
				char[] row = br.readLine().trim().toCharArray();
				for (int c = 0; c < n; c++) {
					board[r][c] = row[c] - '0';
				}
			}
			
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.offer(new Node(0,0,0));
			
			int val = dijkstra(pq, dist, visited, board);
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(i).append(" ").append(val).append("\n");
			System.out.print(sb);
		}
		// 2차원배열 visited관리, dist관리, 노드는 r,c,cost로 구분
			// priorityqueue에서 활용하기 위해 comaparable 구현하기 (compareTo함수)
		// proirityQueue로 (offer/poll) 로 갱신지점 찾기
		// 인접노드 기준으로 dist갱신하기
		
	}
}
