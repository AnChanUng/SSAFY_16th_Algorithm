/*
전략
- BFS
- 시작점에서 모든 dist에 최단거리 표기. 이때 최대값도 저장
- 모든 노드의 dist를 돌면서 최대값과 값이 같은 dist의 개수를 세서 리턴
*/
import java.util.*;

class Solution {
    static List<List<Integer>> graph;
    static int[] dist;
    static int maxN;
    
    public int solution(int n, int[][] edge) {
        maxN = 0; // 가장 멀리 떨어진 노드의 거리
        
        dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[1] = 0; // 시작 지점
        
        graph = new ArrayList<>();
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] e : edge){
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        
        bfs(1);
        
        // maxN과 값이 같은 dist의 개수 찾기
        int answer = 0;
        for(int i = 1; i <= n; i++){
            if(dist[i] == maxN) answer++;
        }
        return answer;
    }
    
    private void bfs(int start){
        // 모든 노드에 최단거리 갱신
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        
        while(!queue.isEmpty()){
            int curr = queue.poll();
            
            for(int next : graph.get(curr)){
                if(dist[next] == -1){
                    dist[next] = dist[curr] + 1;
                    maxN = Math.max(maxN, dist[next]);
                    queue.offer(next);
                }
            }
        }
    }
}