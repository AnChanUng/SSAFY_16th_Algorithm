import java.util.*;
class Solution {
    static List<Integer>[] graph;
    static boolean[] vis;
    static int[] dist;
    public int solution(int n, int[][] edge) {
        graph = new List[n+1];
        
        for(int i=1; i<=n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(int i=0; i<edge.length; i++) {
            int a = edge[i][0];
            int b = edge[i][1];
            graph[a].add(b);
            graph[b].add(a);
        }
        //System.out.println(Arrays.toString(graph));
        bfs(1, n);
        
        Arrays.sort(dist);
        
        //System.out.println(Arrays.toString(dist));
        
        int cnt = 1;
        for(int i=n; i>0; i--) {
            if(dist[i] == dist[i-1]) {
                cnt++;
            } else {
                break;
            }
        }
        
        return cnt;
    }
    static void bfs(int start, int n) {
        Deque<Integer> dq = new ArrayDeque<>();
        vis = new boolean[n+1];
        dist = new int[n+1];
        
        dq.offer(start);
        vis[start] = true;
        while(!dq.isEmpty()) {
            //System.out.println(dq);
            int cur = dq.poll();
            for(int next : graph[cur]) {
                if(!vis[next]) {
                    vis[next] = true;
                    //System.out.println("next: " + next + " dist: " + dist[next]);
                    dist[next] = dist[cur] + 1; 
                    dq.offer(next);
                }
            }
        }
    }
}
