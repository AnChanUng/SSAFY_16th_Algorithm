import java.util.*;
import java.lang.*;
import java.io.*;
/*
    *자신의 키가 몇 번째 인지 알 수 있는 학생이 모두 몇명인지

    *알고리즘: DFS, 인접리스트
*/
class SWEA5643 {
    static List<Integer>[] up;
    static List<Integer>[] down;
    static boolean[] vis;
    static int n, m;
    static int cnt;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=t; tc++) {
            n = Integer.parseInt(br.readLine()); // 학생 수
            m = Integer.parseInt(br.readLine()); // 학생 키 비교 횟수

            up = new List[n+1];
            down = new List[n+1];
            for(int i=1; i<=n; i++) {
                up[i] = new ArrayList<>();
                down[i] = new ArrayList<>();
            }
            
            for(int i=0; i<m; i++) {
                st = new StringTokenizer(br.readLine());
                
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                up[a].add(b);
                down[b].add(a);
            }
        
            int answer = 0;
            int total;
            
            for(int i=1; i<=n; i++) {
                vis = new boolean[n+1];
                cnt = 0;
                vis[i] = true;
                dfs(i, up);
                total = cnt;
                
                vis = new boolean[n+1];
                cnt = 0;
                vis[i] = true;
                dfs(i, down);
                total += cnt;
                
                if(total == n-1) answer++;
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
    }
    static void dfs(int node, List<Integer>[] graph) {
        for(int next : graph[node]) {
            if(!vis[next]) {
                vis[next] = true;
                cnt++;
                dfs(next, graph);
            }
        }
    }
}
