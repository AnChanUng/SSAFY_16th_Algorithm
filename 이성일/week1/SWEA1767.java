package swtb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
어려웠거나 몰랐던 점:
1.자바로 짜는 알고리즘에 익숙하지 않아 최적화 된 설계에 확신이 없다
2. 성급하게 코드나 논리를 짜려는 경향, 확실하게 검증된 논리와 코드만 사용하기
3. 엣지케이스 -> 엣지로직 (전체 범위를 아우르는 로직) */

public class SWEA1767 {
	
	public static int[] ans = {0,144};
	
	public static int[] dx = {-1, 0, 1, 0};
	public static int[] dy = {0, 1, 0,  -1};
	
	public static boolean isSearchable(int[][] board, int x, int y, int vx, int vy) {
		x += vx; y += vy;
		while (x >= 0 && x < board.length && y >= 0 && y < board.length) {
			if (board[x][y] != 0) {
				return false;
			}
			x += vx;
			y += vy;
		}
		return true;
		
	}
	
	public static int draw(int[][] board, int x, int y, int vx, int vy, int draw) {
		x += vx; y += vy;
		int wires = 0;
		while (x >= 0 && x < board.length && y >= 0 && y < board.length) {
			board[x][y] = draw;
			wires+=1;
			x += vx; y += vy;
		}
		return wires;
		
	}
	
	
	// 3. 노드인덱스 리스트 기준 DFS 진행하기
			// 3-1. 각 노드 기준 4방향 탐색
				// 3-1-2. 방향 인덱스 별로 탐색 후, 0일 경우 -1로 표기 후 다음 탐색 진행
				// 3-1-3. 탐색 불가 방향일 경우, 미 탐색으로 상태 진행
				// 3-1-4. 롤백, 미탐색 스테이지는 함수 인자값만
			// 3-2. 모든 노드 탐색 완료 시 최대노드 수와 비교 후 (동일 시 전선길이 최소값으로) 탐색 종료
	public static void dfs(int nIdx, int searchCnt, int totalWires,
			int[][] board, int[] nodes) {
		
		int nodeLength = nodes.length / 2;
		if (nIdx == nodeLength) {
			if (ans[0] < searchCnt) {
				ans[0] = searchCnt;
				ans[1] = totalWires;
			} else if (ans[0] == searchCnt) {
				ans[1] = Math.min(ans[1], totalWires);
			}
			return;
		}
		
		boolean flag = false;
		for (int i = 0; i < dx.length; i++) {
			int x = nodes[nIdx * 2], y = nodes[nIdx * 2 + 1];
			int vx = dx[i], vy = dy[i];
			if (isSearchable(board, x, y, vx, vy)) {
				flag = true;
				int wires = draw(board, x,y,vx,vy,-1);
				dfs(nIdx+1, searchCnt+1, totalWires+wires, board, nodes);
				draw(board,x,y,vx,vy,0);
			} else {
				continue;
			}
		}
		if (!flag) dfs(nIdx+1, searchCnt, totalWires, board, nodes);
		// *연결가능함에도 미연결하는 탐색이 필요할 수 있다.*
		else dfs(nIdx+1, searchCnt, totalWires, board, nodes);
	}
	
	public static void main(String[] args) throws Exception {
		// 1. 입출력 정의 (tc 입력 받기, 출력 양식은 #tc ans)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		
		
		
		// 2. 보드 입력 받고, 노드 인덱스 관리 , 엣지 노드는 관리 x
		for(int i = 1; i <=tc; i++) {
			int n = Integer.parseInt(br.readLine());
			int[][] board = new int[n][n];
			int[] nodes = new int[24];
			int nodeCnt = 0;
			int idx = 0;
			StringTokenizer st;
			for (int r = 0; r < n; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c = 0; c < n; c++) {
					int val = Integer.parseInt(st.nextToken());
					board[r][c] = val;
					if ((val > 0) && (r > 0) && (r < (n -1)) &&
							(c > 0) && (c < (n - 1))) {
						nodes[idx++] = r;
						nodes[idx++] = c;
						nodeCnt++;
					}
				}
			}
			
			
			int[] rNodes = new int[nodeCnt*2];
			for (int a = 0, b = 0; a < nodeCnt*2; a++) {
				rNodes[b++] = nodes[a];
			}
			

			dfs(0,0,0,board,rNodes);
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(i).append(" ").append(ans[1]).append("\n");
			System.out.print(sb);
			// static 변수 초기화로 테스트케이스별로 독립적인 실행
			ans[0] = 0;
			ans[1] = 0;
		}
			
	}

}
