import java.util.*;

class Solution {
    static ArrayList<Edge>[] graph;
    
    static class Edge {
        int to;
        int cost;
        
        Edge(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
    }
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        graph = new ArrayList[n + 1];
        
        for(int i = 0; i <= n; i++){
            graph[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < fares.length; i++){
            int start = fares[i][0];
            int end = fares[i][1];
            int cost = fares[i][2];
            
            graph[start].add(new Edge(end, cost));
            graph[end].add(new Edge(start, cost));
        }
        
        int[] costS = dijkstra(s, n);
        int[] costA = dijkstra(a, n);
        int[] costB = dijkstra(b, n);
        
        for(int i = 1; i <= n; i++) {
            answer = Math.min(answer, costS[i] + costA[i] + costB[i]);
        }
        
        return answer;
    }
    
    static int[] dijkstra(int start, int n) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        
        dist[start] = 0;
        pq.offer(new Edge(start, 0));
        
        while(!pq.isEmpty()) {
            Edge current = pq.poll();
            
            if(dist[current.to] < current.cost) continue;
            
            for(Edge next : graph[current.to]) {
                if(dist[next.to] > dist[current.to] + next.cost) {
                    
                    dist[next.to] = dist[current.to] + next.cost;
                    pq.offer(new Edge(next.to, dist[next.to]));
                }
            }
        }
        
        return dist;
    }
}
