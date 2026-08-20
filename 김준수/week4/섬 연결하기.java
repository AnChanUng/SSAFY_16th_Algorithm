/*
전략
- MST = 최소 비용으로 모든 섬이 다 연결되게, 무방향 가중치 그래프
- 유니온 파인드 구현, 크루스칼 구현 템플릿 코드를 아직 못외움
*/

import java.util.*;

class Solution {
    static int[] parent;
    
    class Edge implements Comparable<Edge>{
        int u;
        int v;
        int cost;
        
        Edge(int u, int v, int cost){
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge e){
            return this.cost - e.cost;
        }
    }
    
    public int solution(int n, int[][] costs) {
        // Makeset 초기화
        parent = new int[n + 1];
        for(int i = 1; i <= n; i++){
            parent[i] = i;
        }
        
        // 간선 초기화 및 정렬
        List<Edge> edges = new ArrayList<>();
        for(int[] c : costs){
            edges.add(new Edge(c[0], c[1], c[2]));
        }
        Collections.sort(edges);

        int totalCost = 0; // 최소 비용 저장
        int edgeCnt = 0; // 간선 개수 저장(종료 조건)
        
        // 크루스칼
        for(Edge e : edges){
            // 두 정점이 같은 그래프가 아니면 합치고 비용, 간선 수 갱신
            if(find(e.u) != find(e.v)){
                union(e.u, e.v);
                totalCost += e.cost;
                edgeCnt++;
            }
            
            // 최소 연결하려면 간선 개수가 n-1개여야 함
            if(edgeCnt == n - 1){
                break;
            }
        }
        
        return totalCost;
    }
    
    public int find(int x){
        if(parent[x] == x){
            return x;
        }
        return parent[x] = find(parent[x]);
    }
    
    public void union(int x, int y){
        x = find(x);
        y = find(y);
        
        if(x != y){
            parent[x] = y;
        }
    }
}