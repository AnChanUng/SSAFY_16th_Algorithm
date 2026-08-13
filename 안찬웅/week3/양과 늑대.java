import java.util.*;
/* 
    이동할때마다 누적해서 양을 최대로 모을 수 있는 최대의 수
    조건: 내가 모은 양의 수 <= 늑대의 수 
    -> 모든양을 잡아먹어버림 안됨
    
    알고리즘: 그래프, 단방향 인접리스트
    
    1. edges 정보로 단방향 그래프 생성
    2. 0번노드 방문시작. 0번 노드의 자식을 candidates에 넣음
    3. dfs
     3.1 candidates에 있는 노드들을 하나씩 순회 다음 방문 후보 선택
     3.2 next를 방문했을때 양과 늑대 계산
     3.3 다음늑대수 >= 다음양의수이면 continue
     3.4 next방문 처리후 candidates에서 next를 제거하고 next의 안가본 자식을 candidates에 추가
     3.5 최대양의수 갱신
     3.6 추가했던 자식 제거, next를 원래 위치로 복구 후 방문취소
    4. 최대야의 수 출력
*/
class Solution {
    static List<Integer>[] graph;
    static int[] info;
    static int maxSheep;
    public int solution(int[] info, int[][] edges) {
        this.info = info;
        maxSheep = 1;
        int nodeLen = info.length;
        
        graph = new List[nodeLen];
        for(int i=0; i<nodeLen; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(int i=0; i<edges.length; i++) {
            int parent = edges[i][0];
            int child = edges[i][1];
            graph[parent].add(child);
        }
        
        List<Integer> candidates = new ArrayList<>();
        
        for(int child : graph[0]) {
            candidates.add(child);
        }
        
        dfs(candidates, 0, 1);
        
        return maxSheep;
    }
    
    static void dfs(List<Integer> candidates, int sumWolf, int sumSheep) {
        for(int i=0; i<candidates.size(); i++) {
            int next = candidates.get(i);
            
            int nextSheep = sumSheep;
            int nextWolf = sumWolf;

            if (info[next] == 0) {
                nextSheep++;
            } else {
                nextWolf++;
            }
            
            if (nextWolf >= nextSheep) continue;
            
            maxSheep = Math.max(maxSheep, nextSheep);
            
            List<Integer> nextCandidates = new ArrayList<>(candidates);
            nextCandidates.remove(i);

            for(int child : graph[next]) {
                nextCandidates.add(child);
            }
            
            dfs(nextCandidates, nextWolf, nextSheep);
        }
    }
}
