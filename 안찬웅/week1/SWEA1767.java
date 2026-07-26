import java.util.*;
import java.io.*;

/*
 * 각 Core의 위치에 상하좌우중에 가장 가까운 곳 길이의 합
 * 
 * 멕시노스: n * n
 * 빈 cell: 0, core: 1
 * 
 * 알고리즘: 백트래킹
 * 
 */
public class SWEA1767 {
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	static int[][] arr; // 격자판
	static List<int[]> cores; // 탐색 대상 코어 좌표
	static int maxCore; // 연결한 코어 개수 최대값
	static int n;
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int t = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=t; tc++) {
			n = Integer.parseInt(br.readLine());
			
			arr = new int[n][n];
			cores = new ArrayList<>();
			maxCore = 0;
			
			for(int i=0; i<n; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<n; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken()); 
				}
			}
			
			dfs(0, 0, 0);
			System.out.println("#" + tc + " ");
		}
	}
	static void dfs(int idx, int cnt, int sum) {
		if(idx == cores.size()) {
			return;
		}
		
	}
}
