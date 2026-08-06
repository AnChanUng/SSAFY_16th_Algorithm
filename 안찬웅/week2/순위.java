import java.util.*;
/*
    정확하게 순위를 알 수 있는 사람은 몇명인가

    알고리즘: 그래프 DFS, 인접리스트
    
    진사람 수 + 이긴사람 수 == 전체사람수-1 cnt++;
*/
class Solution {
    static List<Integer>[] win;
    static List<Integer>[] lose;
    static boolean[] vis;
    static int cnt;
    public int solution(int n, int[][] results) {
        win  = new List[n+1];
        lose = new List[n+1];
        int answer = 0;
        int total;
        
        for(int i=1; i<=n; i++) {
            win[i] = new ArrayList<>();
            lose[i] = new ArrayList<>();
        }
        
        for(int i=0; i<results.length; i++) {
            int winner = results[i][0];
            int loser = results[i][1];
            win[winner].add(loser);
            lose[loser].add(winner);
        }

        for(int i=1; i<=n; i++) {
            // win
            vis = new boolean[n+1];
            cnt = 0;
            vis[i] = true;
            dfs(i, win);
            total = cnt;
            
            // lose
            vis = new boolean[n+1];
            cnt = 0;
            vis[i] = true;
            dfs(i, lose);
            total += cnt;
            
            // (이긴사람 수 + 진사람 수) == 전체사람수 - 1
            if(total == n-1) answer++;
            
        }
        return answer;
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
