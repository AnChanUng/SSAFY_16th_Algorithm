import java.io.*;
import java.util.*;

/*
 * 어려웠던 점: 처음에 백트래킹하는 과정에서 초기화를 안해서 값이 꼬임, 이후에 리팩토링 하는 과정에서 탐색할 필요가 없는 경우에만 리턴하게 해야하는 데 미처 생각하지 못함
 * 함수 네이밍을 보통 내가 보기 편한대로 작성하였는데 스터디를 진행하면서 좀 더 와닿는 네이밍 명을 작성해될 것 같
 */

public class SWEA1767 {

	static int[][] map;
	static int N;
	static ArrayList<int[]> cores;
	static int result;
	static int maxConnected;

	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			N = Integer.parseInt(br.readLine());
			map = new int[N][N];

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			simulate();

			System.out.println("#" + tc + " " + result);
		}
	}

	static void simulate() {

		cores = new ArrayList<>();

		for (int i = 1; i < N - 1; i++) {
			for (int j = 1; j < N - 1; j++) {
				if (map[i][j] == 1) {
					cores.add(new int[]{i, j});
				}
			}
		}

		maxConnected = 0;
		result = Integer.MAX_VALUE;

		dfs(0, 0, 0);
	}

	static void dfs(int index, int connectedCnt, int wireLength) {

		int remain = cores.size() - index;

		if (connectedCnt + remain < maxConnected) {
			return;
		}

		if (index == cores.size()) {

			if (connectedCnt > maxConnected) {
				maxConnected = connectedCnt;
				result = wireLength;
			} else if (connectedCnt == maxConnected) {
				result = Math.min(result, wireLength);
			}

			return;
		}

		int x = cores.get(index)[0];
		int y = cores.get(index)[1];

		for (int dir = 0; dir < 4; dir++) {

			int length = check(x, y, dir);

			if (length > 0) {

				setWire(x, y, dir, 2);

				dfs(index + 1,connectedCnt + 1,wireLength + length);

				setWire(x, y, dir, 0);
			}
		}

		dfs(index + 1, connectedCnt, wireLength);
	}

	static int check(int x, int y, int dir) {

		int nx = x + dx[dir];
		int ny = y + dy[dir];
		int length = 0;

		while (inRange(nx, ny)) {

			if (map[nx][ny] != 0) {
				return 0;
			}

			length++;
			
			nx += dx[dir];
			ny += dy[dir];
		}

		return length;
	}

	static void setWire(int x, int y, int dir, int value) {

		int nx = x + dx[dir];
		int ny = y + dy[dir];

		while (inRange(nx, ny)) {
			map[nx][ny] = value;
			nx += dx[dir];
			ny += dy[dir];
		}
	}

	static boolean inRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
}