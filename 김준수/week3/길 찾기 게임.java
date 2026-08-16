/*
전략
- 이진 탐색 트리 구현
    - 각 노드들을 y 기준 내림차순으로 정렬하고 이진 탐색 트리에 하나씩 넣기
    - 새 노드를 넣었을 때 현재 노드의 x 값과 비교해서 왼쪽 오른쪽을 분류
        - 루트 노드부터 시작
    - 해당 방향의 자식이 비었으면 넣고, 안 비었으면 다음 자식으로 이동해서 다시 x값 비교
- dfsPre : 이진트리를 전위순회 ⇒ 루트 → 왼쪽 → 오른쪽 순으로 결과 리스트에 추가
    - 전위순회는 현재 노드를 먼저 결과에 추가하고 왼쪽 호출 → 오른쪽 호출
- dfsPost : 이진트리를 후위순회 ⇒ 왼쪽 → 오른쪽 → 루트 순으로 결과 리스트에 추가
    - 후위순회는 왼쪽 호출 → 오른쪽 호출 한 후 현재 노드 추가

시행착오
- 이진 탐색 트리 문제임을 늦게 파악하고 순수 그래프로 구현 시도
  중간에 그래프에 노드 추가할 때 조건 탐색 과정이 너무 복잡해서 포기하고 이진 탐색 트리로 선회
- 이진 탐색 트리 구현 템플릿이 있을 것 같은데, 생각나는 대로 구현해서 코드가 더러울 듯
*/

import java.util.*;

class Solution {
    static Node[] nodes;
    static List<Integer> preList;
    static List<Integer> postList;
    
    class Node {
        int x;
        int y;
        int left;
        int right;
        int value;
        
        Node(int x, int y, int value){
            this.x = x;
            this.y = y;
            this.value = value;
            left = -1;
            right = -1;
        }
    }
    
    class BST{
        Node root;
        int size;
        
        BST(Node root){
            this.root = root;
            size = 1;
        }
        
        public void add(Node child){
            size++;
            Node curr = root;
            
            while(true){
                // child가 왼쪽일지 오른쪽일지
                if(child.x < curr.x){
                    // -1이면 왼쪽 자식에 삽입 후 종료 
                    if(curr.left == -1){
                        curr.left = child.value;
                        break;  
                    }
                    // -1이 아니면 다음 왼쪽 자식으로
                    else{
                        curr = nodes[curr.left];
                    }
                }
                else{
                    // -1이면 오른쪽 자식에 삽입 후 종료
                    if(curr.right == -1){
                        curr.right = child.value;
                        break;
                    }
                    // -1이 아니면 다음 오른쪽 자식으로
                    else{
                        curr = nodes[curr.right]; 
                    }
                }
            }
        }
    }
    
    public int[][] solution(int[][] nodeinfo) {
        preList = new ArrayList<>();
        postList = new ArrayList<>();
        
        nodes = new Node[nodeinfo.length];
        for(int i = 0; i < nodeinfo.length; i++){
            nodes[i] = new Node(nodeinfo[i][0], nodeinfo[i][1], i);
        }
        // y를 기준으로 내림차순 정렬
        Node[] sortedNodes = Arrays.copyOf(nodes, nodes.length);
        
        Arrays.sort(sortedNodes, (a, b) -> b.y - a.y);
        
        // 루트 노드 삽입
        BST bst = new BST(sortedNodes[0]);
        // 정렬 순서대로 노드 삽입
        for(int i = 1; i < sortedNodes.length; i++){
            bst.add(sortedNodes[i]);
        }
        
        dfsPre(sortedNodes[0]);
        dfsPost(sortedNodes[0]);
        
        int[][] answer = new int[2][nodes.length];
        for(int i = 0; i < preList.size(); i++){
            answer[0][i] = preList.get(i);
            answer[1][i] = postList.get(i);
        }
        
        return answer;
    }
    
    private void dfsPre(Node curr){
        if(preList.size() == nodes.length){
            return;
        }
        
        preList.add(curr.value + 1);
        
        if(curr.left != -1){
            dfsPre(nodes[curr.left]);
        }
        
        if(curr.right != -1){
            dfsPre(nodes[curr.right]);
        }
    }
    
    private void dfsPost(Node curr){
        if(postList.size() == nodes.length){
            return;
        }
        
        if(curr.left != -1){
            dfsPost(nodes[curr.left]);
        }
        
        if(curr.right != -1){
            dfsPost(nodes[curr.right]);
        }
        
        postList.add(curr.value + 1);
    }
}