import java.util.*;
/*
    가중치 최단 거리 구하기
        
    알고리즘: 다익스트라, 우선순위큐, 인접리스트
    
    n: 지점의 개수 (정점의 개수)
    s: 출발지점
    a: A의 도착지점
    b: B의 도착지점
    fares[i] = {정점1, 정점2, 요금}

    핵심: 갈라지는 지점 k를 정하면 총요금 = (s→k) + (k→a) + (k→b)
    
    1. fares로 양방향 가중치 인접리스트 생성
    2. s, a, b 각각에서 다익스트라 → distS, distA, distB
    3. k를 1~n 완전탐색하며 세 항 합의 최소 갱신
*/
class Solution {
    static List<Node>[] list;
    static int N;
    static int minNum;
    static class Node implements Comparable<Node> {
        int v;
        int cost;
        
        Node(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
        
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
    public int solution(int n, int s, int a, int b, int[][] fares) {
        N = n;
        minNum = Integer.MAX_VALUE; // 가중치 최소합
        
        list = new List[n+1];
        for(int i=1; i<=n; i++) {
            list[i] = new ArrayList<>();
        }
            
        for(int i=0; i<fares.length; i++) {
            int from = fares[i][0];
            int to = fares[i][1];
            int cost = fares[i][2];
            list[from].add(new Node(to, cost));
            list[to].add(new Node(from, cost));
        }
        
        int[] distS = dijkstra(s);
        int[] distA = dijkstra(a);
        int[] distB = dijkstra(b);
        
        for(int k=1; k<=n; k++) {
            if(minNum > distS[k] + distA[k] + distB[k]) {
                minNum = distS[k] + distA[k] + distB[k];
            }
        }
        
        return minNum;
    }
    static int[] dijkstra(int start) {
        int[] dist = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        
        while(!pq.isEmpty()) {
            // 비용이 가장 작은 노드를 꺼낸다
            Node cur = pq.poll();
            
            // 이미 더 짧은 경로를 찾아둔 상태면 건너뜀
            if(cur.cost > dist[cur.v]) continue;
            
            // 인접 노드를 돌면서 현재까지 비용 + 간선 비용이 기존 dist보다 작으면 갱신
            for(Node next : list[cur.v]) {
                int nCost = cur.cost + next.cost;
                if(nCost < dist[next.v]) {
                    dist[next.v] = nCost;
                    pq.add(new Node(next.v, nCost));
                }
            }
        }
        return dist;
    }
}
