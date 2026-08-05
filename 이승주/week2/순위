import java.util.*;
class Solution { //swea 키 순위 문제와 똑같음
    static int N;
    static ArrayList<Integer>[] graph;
    static ArrayList<Integer>[] reverseGraph;
    public int solution(int n, int[][] results) {
        this.N = n;
        
        graph = new ArrayList[n+1];
        reverseGraph = new ArrayList[n+1];
        
        for(int i = 0; i <= n; i++){
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < results.length; i++){
            int from = results[i][0];
            int to = results[i][1];
            
            graph[from].add(to);
            reverseGraph[to].add(from);
        }
        int cnt = 0;
        for(int i = 1; i <= n; i++){
            int a = bfs(i,graph);
            int b = bfs(i,reverseGraph);
            
            if(a+b == n-1){
                cnt++;
            }
        }
        
        return cnt;
        
        
    }
    
    static int bfs(int start, ArrayList<Integer>[] currentGraph){
        
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N+1];
        visited[start] = true;
        int cnt = 0;
        
        q.offer(start);
        
        while(!q.isEmpty()){
            
            Integer current = q.poll();
            
            for(Integer next : currentGraph[current]){
                
                if(!visited[next]){
                    q.offer(next);
                    visited[next] = true;
                    cnt++;
                }
                
            }
        }
        
        return cnt;
    }
}
