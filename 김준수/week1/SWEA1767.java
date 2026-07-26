/*

프로세서 연결하기 7/27
40,452kb 284ms

전략
- 완전탐색 백트래킹
    - 각 코어의 모든 선택지를 모든 코어에 대해 백트래킹하여 탐색한다
- 그리드의 가장자리에 있는 코어는 결과값에 영향을 주지 못하기 때문에 백트래킹 목록에서 제외한다
- 풀이 로직
    - 백트래킹 인자로 인덱스, 코어 배열, 전원이 연결된 코어 개수를 넘긴다
    - 코어 배열에서 현재 인덱스의 코어를 꺼내서 상하좌우 4방향에 대해 전원 연결이 가능한지 검사(canConnect 함수)
    - 전원 연결이 가능하다면 해당 경로의 board를 2로 업데이트(connect 함수)
    - 인덱스 1 추가해서 재귀 호출
    - board에서 연결했던 전선을 다시 빈칸으로 초기화(unconnect 함수)
    - 상하좌우 4방향을 다 돌았으면, 아예 연결하지 않고 넘어가는 재귀 호출 진행
    - 만약 인덱스를 끝까지 다 돌았다면 종료 조건 검사
        - 전원이 연결된 코어 개수가 기존 답변보다 많으면 무조건 갱신
        - 전원이 연결된 코어 개수가 기존 답변과 같으면 더 전선 수가 적은 쪽으로 갱신
        - 전원이 연결된 코어 개수가 기존 답변보다 적으면 결과 버림

시행착오
- 처음에는 상하좌우만 탐색했는데 최적의 답을 절대 얻을 수가 없었음. 이후 “코어에 연결을 아예 하지 않는다”라는 선택지를 상하좌우에 더해 추가해서 총 5번의 탐색을 진행해서 해결
- 기존에는 코어가 가장자리에 위치하면 해당 코어는 탐색 스킵하고 백트래킹 깊이 하나 더 들어가는 식으로 구현했는데, 상하좌우무 문제 시행착오하는 과정에서 가장자리 코어는 아예 cores 배열에 추가를 안해버려도 답변에 차이가 없다는 사실을 깨닫고 수행 시간을 약간 줄일 수 있었음

*/

import java.io.*;
import java.util.*;

public class SWEA1767 {
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int[][] board;
	static int result;
	static int lineCnt;
	static int completeResult;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			result = Integer.MAX_VALUE;
			completeResult = 0; // 전선 수가 result였을 때 연결 완료된 코어의 개수
			lineCnt = 0; // 현재 전선 수
			List<int[]> cores = new ArrayList<>();
			
			for(int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
					
					if(board[i][j] == 1) {
						// 코어가 가장자리에 있으면 cores에 아예 추가하지 않음
						// 가장자리의 코어는 장애물 역할 말고는 아예 영향 없기 때문
						if(i == 0 && i == N - 1 && j == 0 && j == N - 1) continue;
						cores.add(new int[] {i, j});
					}
				}
			}
			
			backtrack(0, cores, 0);
			
			sb.append("#").append(t).append(" ").append(result).append("\n");
		}
		
		System.out.print(sb);
	}
	
	// 현재 {x, y} 에서 direction 방향으로 뻗었을 때 전력이 연결될 수 있는지 여부를 반환
	private static boolean canConnect(int x, int y, int direction) {
		boolean result = true;
		
		for(int i = 1; i < board.length; i++) {
			int nx = x + dx[direction] * i;
			int ny = y + dy[direction] * i;
			
			if(nx < 0 || nx >= board.length || ny < 0 || ny >= board.length) {
				break;
			}
			
			if(board[nx][ny] != 0) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	// canConnect가 true면 해당 입력대로 실제로 board를 수정
	private static int connect(int x, int y, int direction) {
		int count = 0;
		
		for(int i = 1; i < board.length; i++) {
			int nx = x + dx[direction] * i;
			int ny = y + dy[direction] * i;
			
			if(nx < 0 || nx >= board.length || ny < 0 || ny >= board.length) {
				break;
			}
			
			board[nx][ny] = 2;
			count++;
		}
		
		return count;
	}
	
	// 백트래킹 depth 1 추가 이후 원상태로 되돌리기
	private static void unconnect(int x, int y, int direction, int length) {
		for(int i = 1; i <= length; i++) {
			int nx = x + dx[direction] * i;
			int ny = y + dy[direction] * i;
			
			board[nx][ny] = 0;
		}
	}
	
	private static void backtrack(int i, List<int[]> cores, int completeCnt) {
		// 종료 조건 : 마지막 코어까지 다 방문 완료
		if(i == cores.size()) {
			// 전원 연결된 코어 개수가 더 많으면 전선 수와 상관없이 갱신
			if(completeResult < completeCnt) {
				completeResult = completeCnt;
				result = lineCnt;
			}
			// 전원 연결된 코어 개수가 같으면 전선 수가 더 작은 쪽으로 갱신
			else if(completeResult == completeCnt) {
				result = Math.min(result, lineCnt);
			}
			return;
		}
		
		int[] core = cores.get(i);
		int x = core[0];
		int y = core[1];
		
		if(x == 0 || y == 0 || x == board.length - 1 || y == board.length - 1) {
			backtrack(i + 1, cores, completeCnt + 1);
			return;
		}
		
		// 현재 코어에서 4방향 탐사
		for(int j = 0; j < 4; j++) {
			// 만약 해당 방향으로 전선 뻗었을 때 전원 연결이 가능하면
			if(canConnect(x, y, j)) {
				// 전원 연결하고 전선 개수만큼 lineCnt 증가
				int len = connect(x, y, j);
				lineCnt += len;
				
				// 백트래킹 깊이 1 더 들어가기
				backtrack(i + 1, cores, completeCnt + 1);
				
				// 연결했던 전선을 다시 0으로 돌리고 lineCnt 감소
				lineCnt -= len;
				unconnect(x, y, j, len);
			}
		}
		
		// * 연결하지 않고 넘어가기 로직 추가
		backtrack(i + 1, cores, completeCnt);
	}
}
