/*
전략.
- 다익스트라 완전탐색
- s, a, b에서 시작할때의 최단거리 배열을 다익스트라를 3번 돌려 얻음
    - distS[i] : s → i까지의 최단 거리 == i → s까지의 최단 거리
- 초기 결과 값은 s→a + s→b == a→s + b→s == distA[s] + distB[s]
- 모든 합승 가능 지점 i에 대해(s 제외) s→i +i→a + i→b 의 요금을 찾아 결과 값과 비교 갱신
    - s→i +i→a + i→b == s→i + a→i + b→i == distS[i] + distA[i] + dist[B]
*/

import java.util.*;

class Solution {
    static List<List<Node>> graph;
    
    class Node implements Comparable<Node>{
        int target;
        int cost;
        
        Node(int target, int cost){
            this.target = target;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Node n){
            return Integer.compare(this.cost, n.cost);
        }
    }
    
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        // 인접 리스트 초기화
        graph = new ArrayList<>();
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] fee : fares){
            graph.get(fee[0]).add(new Node(fee[1], fee[2]));
            graph.get(fee[1]).add(new Node(fee[0], fee[2]));
        }
        
        // s, a, b에서 시작할 때에 대한 최단거리 배열 찾기
        int[] distS = new int[n + 1];
        int[] distA = new int[n + 1];
        int[] distB = new int[n + 1];
        for(int i = 0; i <= n; i++){
            distS[i] = Integer.MAX_VALUE;
            distA[i] = Integer.MAX_VALUE;
            distB[i] = Integer.MAX_VALUE;
        }
        dijkstra(s, distS);
        dijkstra(a, distA);
        dijkstra(b, distB);
        
        // 초기 결과값 : 합승 없이 각자 진행
        int answer = distA[s] + distB[s];
        
        // 모든 노드 i를 순회
        // s->i + a->i + b->i = i까지 합승하는 최소요금
        // ** a->i == i->a 이므로 이렇게 계산해도 가능 **
        for(int i = 1; i <= n; i++){
            if(i == s){
                continue;
            }
            int cost = distS[i] + distA[i] + distB[i];
            answer = Math.min(answer, cost);
        }
        
        return answer;
    }
    
    private void dijkstra(int start, int[] dist){
        dist[start] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        
        while(!pq.isEmpty()){
            Node curr = pq.poll();
            
            // curr 노드의 누적 비용이 기존의 dist보다 크면 스킵 
            if(curr.cost > dist[curr.target]){
                continue;
            }
            
            // 인접 노드를 순회
            for(Node next : graph.get(curr.target)){
                // 도달 비용이 기존보다 작다면 큐에 삽입
                int cost = curr.cost + next.cost;
                if(cost < dist[next.target]){
                    dist[next.target] = cost;
                    pq.offer(new Node(next.target, cost));
                }
            }
        }
    }
}