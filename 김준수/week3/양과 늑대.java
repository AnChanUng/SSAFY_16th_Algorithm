/*
전략
- 완전탐색 백트래킹
- Node 클래스 구현. 연결된 Node의 인덱스와 자신의 타입(양=1, 늑대=0)을 저장
    - 각 간선은 [부모, 자식]으로 입력이 주어짐. 따라서 부모 → 자식만 저장하면 역주행 불가
- “현재 접근 가능한 노드 목록”을 파라미터로 넘겨주면서 모든 가능한 탐색을 진행
    - 늑대의 수가 양과 같거나 많아지면 종료(프루닝)
    - 늑대의 수와 양의 수의 합이 전체 노드의 길이과 같으면 종료(종료조건)
    - 다음 노드로 진행할 때마다 현재의 양의 수를 갱신

시행착오
- 모든 간선에 대해 양방향으로 연결했었는데, 방문 처리 및 재방문에 대한 로직이 꼬여서 틀림
    - 부모 → 자식만 연결함으로써 역주행 불가능하게 하여 문제 해결
- 초기에는 sheep, wolf를 Set으로 구현했는데 Set의 deepCopy가 너무 어려웠음
    - sheep, wolf는 숫자로 관리하고, 현재 상태에서 접근 가능한 노드 List를 deepCopy하여 매 재귀 호출마다 새로운 객체를 넘기도록 하여 해결
*/

import java.util.*;

class Solution {
    List<Node> nodes;
    static int answer;
    static boolean[] visited;
    
    class Node{
        boolean isWolf;
        List<Integer> child;
        
        Node(int type){
            isWolf = (type == 1) ? true : false;
            child = new ArrayList<>();
        }
    }
    
    public int solution(int[] info, int[][] edges) {
        answer = 0;
        visited = new boolean[info.length];
        // Node 배열 초기화
        nodes = new ArrayList<>();
        for(int i = 0; i < info.length; i++){
            nodes.add(new Node(info[i]));
        }
        // 모든 간선이 [부모, 자식] 형태로 되어있음
        // 따라서 부모 -> 자식만 connect에 저장
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            
            nodes.get(u).child.add(v);
        }
        
        List<Integer> canGo = new ArrayList<>();
        for(int next : nodes.get(0).child){
            canGo.add(next);
        }
        
        dfs(1, 0, canGo);
        
        return answer;
    }
    
    private void dfs(int sheep, int wolf, List<Integer> canGo){
        // 결과 갱신
        answer = Math.max(answer, sheep);
        
        // 종료 조건
        // 양 + 늑대 = 전체면 모두 순회 완료
        // 양 == 늑대면 잡아먹혀서 종료
        if(sheep + wolf == nodes.size() || sheep == wolf){
            return;
        }
        
        // 백트래킹
        for(int next : canGo){
            // nextCanGo에 canGo를 deepCopy
            List<Integer> nextCanGo = new ArrayList<>();
            for(int idx : canGo){
                if(idx == next){
                    continue;
                }
                nextCanGo.add(idx);
            }
            // nextCanGo에 next의 child를 추가
            for(int idx : nodes.get(next).child){
                nextCanGo.add(idx);
            }
            
            // 동물 추가 후 재귀
            // nextCanGo가 매 next마다 새로 할당
            if(nodes.get(next).isWolf){
                wolf++;
                dfs(sheep, wolf, nextCanGo);
                wolf--;
            }
            else{
                sheep++;
                dfs(sheep, wolf, nextCanGo);
                sheep--;
            }
        }
    }
}