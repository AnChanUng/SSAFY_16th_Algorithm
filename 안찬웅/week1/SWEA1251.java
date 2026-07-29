import java.util.*;
import java.io.*;
/*
    모든 섬을 최소 비용으로 연결 -> MST (크루스칼)
    비용 = E * (거리의 제곱) -> 제곱 그대로 쓰면 되므로 sqrt 불필요
    
    알고리즘: 크루스칼 + Union-Find
    시간: O(N^2 log N^2)  (N=1000 -> 간선 약 50만개)
    
    1. 입력
    1.1 N, x좌표 N개, y좌표 N개, 세율 E
    2. 완전그래프 간선 생성 (i < j)
    3. 비용 오름차순 정렬
    4. Union-Find로 사이클 체크하며 N-1개 채택
    5. 총합 * E -> 반올림 출력
*/
class SWEA1251 {
    static int[] parent;
    static int n;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++) {
            n = Integer.parseInt(br.readLine());

            long[] x = new long[n];
            long[] y = new long[n];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) x[i] = Long.parseLong(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) y[i] = Long.parseLong(st.nextToken());

            double e = Double.parseDouble(br.readLine().trim());

            // 2. 간선 생성 (cost, from, to)
            long[][] edges = new long[n * (n - 1) / 2][3];
            int idx = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    long dx = x[i] - x[j];
                    long dy = y[i] - y[j];
                    edges[idx][0] = dx * dx + dy * dy;
                    edges[idx][1] = i;
                    edges[idx][2] = j;
                    idx++;
                }
            }

            // 3. 비용 오름차순 정렬
            Arrays.sort(edges, (o1, o2) -> Long.compare(o1[0], o2[0]));

            // 4. 크루스칼
            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;

            long total = 0;
            int cnt = 0;
            for (long[] edge : edges) {
                int a = (int) edge[1];
                int b = (int) edge[2];

                if (find(a) == find(b)) continue;   // 사이클이면 버림

                union(a, b);
                total += edge[0];
                cnt++;
                if (cnt == n - 1) break;
            }

            sb.append("#").append(tc).append(" ")
              .append(Math.round(total * e)).append("\n");
        }
        System.out.print(sb.toString());
    }

    static int find(int a) {
        if (parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) parent[rootA] = rootB;
    }
}