/*
전략
- 모든 정점에 대해, 나보다 큰 사람의 수 찾기
- 모든 정점에 대해, 나보다 작은 사람의 수 찾기
- 큰 사람 + 작은 사람 == N - 1이면 자신을 제외한 모든 사람의 상대 위치를 알기 때문에 자신의 순위를 알 수 있다.
*/

import java.util.*;
import java.io.*;

public class SWEA5643 {
	static List<List<Integer>> bigGraph;
	static List<List<Integer>> smallGraph;
	static boolean[] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			int M = Integer.parseInt(br.readLine());
			int result = 0;

			visited = new boolean[N + 1];
			bigGraph = new ArrayList<>(); // 자신보다 큰 사람을 저장
			smallGraph = new ArrayList<>(); // 자신보다 작은 사람을 저장
			for (int i = 0; i <= N; i++) {
				bigGraph.add(new ArrayList<>());
				smallGraph.add(new ArrayList<>());
			}

			for (int i = 0; i < M; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());

				bigGraph.get(u).add(v);
				smallGraph.get(v).add(u);
			}

			for (int i = 1; i <= N; i++) {
				visited[i] = true;
				int cnt = findBigCnt(i); // 자기보다 큰 사람 수 찾기
				cnt += findSmallCnt(i); // 자기보다 작은 사람 수 찾기

				// 자기보다 작은사람, 큰 사람 수가 N - 1이면 자신의 순위를 알 수 있음
				if (cnt == N - 1) { 
					result++;
				}
				
				visited = new boolean[N + 1];
			}

			sb.append("#").append(t).append(" ").append(result).append("\n");
		}
		System.out.print(sb);
	}

	static int findBigCnt(int i) {
		int cnt = 0;

		for (int next : bigGraph.get(i)) {
			// 중복 세기 방지
			if(!visited[next]) {
				visited[next] = true;
				cnt++;
				cnt += findBigCnt(next);
			}
		}

		return cnt;
	}

	static int findSmallCnt(int i) {
		int cnt = 0;

		for (int next : smallGraph.get(i)) {
			// 중복 세기 방지
			if(!visited[next]) {
				visited[next] = true;
				cnt++;
				cnt += findSmallCnt(next);
			}
		}

		return cnt;
	}
}
