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
 * 1. 
 */
import java.util.*;
import java.io.*;
/*
 * 각 Core를 상하좌우 중 한 방향으로 격자 밖까지 연결 (전선끼리 교차 불가)
 * 1순위: 연결한 코어 개수 최대 / 2순위: 전선 길이 합 최소
 * 
 * 멕시노스: n * n
 * 빈 cell: 0, core: 1, 전선: 2
 * 
 * 알고리즘: 백트래킹
 * 
 * 1. 가장자리 코어는 전선 0으로 이미 연결 → 탐색 대상에서 제외
 * 2. 내부 코어마다 4방향 + 포기 = 5지선다 완전탐색
 * 3. 경로 칠하기 → 재귀 → 되돌리기
 */
public class SWEA1767 {
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	static int[][] arr; // 격자판
	static List<int[]> cores; // 탐색 대상 코어 좌표
	static int maxCore; // 연결한 코어 개수 최대값
	static int minLen; // maxCore개 연결 시 최소 전선 길이
	static int n;
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int t = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=t; tc++) {
			n = Integer.parseInt(br.readLine());
			
			arr = new int[n][n];
			cores = new ArrayList<>();
			maxCore = 0;
			minLen = Integer.MAX_VALUE;
			
			for(int i=0; i<n; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<n; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken()); 
				}
			}
			
			// 가장자리 제외한 내부 코어만 수집
			for(int i=1; i<n-1; i++) {
				for(int j=1; j<n-1; j++) {
					if(arr[i][j] == 1) {
						cores.add(new int[]{i, j});
					}
				}
			}
			
			dfs(0, 0, 0);
			sb.append("#").append(tc).append(" ").append(minLen).append("\n");
		}
		System.out.print(sb);
	}
	
	static void dfs(int idx, int cnt, int sum) {
		if(idx == cores.size()) {
			if(cnt > maxCore) {
				maxCore = cnt;
				minLen = sum;
			} else if(cnt == maxCore) {
				minLen = Math.min(minLen, sum);
			}
			return;
		}
		
		// 남은 코어 다 연결해도 최대치 못 넘으면 컷
		if(cnt + (cores.size() - idx) < maxCore) return;
		
		int x = cores.get(idx)[0];
		int y = cores.get(idx)[1];
		
		for(int d=0; d<4; d++) {
			int len = check(x, y, d);
			if(len == -1) continue;
			
			fill(x, y, d, 2);
			dfs(idx+1, cnt+1, sum+len);
			fill(x, y, d, 0);
		}
		
		dfs(idx+1, cnt, sum); // 이 코어는 연결 포기
	}
	
	// d방향으로 밖까지 뚫려있으면 길이, 막히면 -1
	static int check(int x, int y, int d) {
		int len = 0;
		int nx = x + dx[d];
		int ny = y + dy[d];
		
		while(nx >= 0 && nx < n && ny >= 0 && ny < n) {
			if(arr[nx][ny] != 0) return -1;
			len++;
			nx += dx[d];
			ny += dy[d];
		}
		return len;
	}
	
	static void fill(int x, int y, int d, int val) {
		int nx = x + dx[d];
		int ny = y + dy[d];
		
		while(nx >= 0 && nx < n && ny >= 0 && ny < n) {
			arr[nx][ny] = val;
			nx += dx[d];
			ny += dy[d];
		}
	}
}