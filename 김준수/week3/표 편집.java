/*
전략
- 링크드 리스트 구현
    - java의 LinkedList, ArrayList는 각 아이템마다 Node를 생성하고 조작해야 해서 remove, insert 작업이 오래 걸림
    - 자신의 앞 뒤를 가리키는 더블 링크드 리스트를 배열 2개로 만들어서 더 빠르게 가능
- 되돌리기는 Stack으로
    - 되돌리기 하면 Stack을 pop해서 기존의 idx를 하나 뒤로 밀고 들어감
        - 선택 행이 되돌리기한 행보다 idx가 큰 경우 idx + 1해야 함
    - 삭제하면 뒤 idx를 자기 쪽으로 당겨오고 Stack에 push함
        - 선택 행이 삭제 행보다 idx가 큰 경우 idx - 1 해야 함

시행착오
- Chart 클래스를 구현해서 멤버 변수와 멤버 함수로 모든 로직 구성
java.util의 클래스를 사용하니 시간초과 발생함
    - Chart 내부에 ArrayList로 관리. remove나 insert로 로직 구현
- AI 도움 받음(링크드 리스트 직접 구현하는 아이디어)
*/
import java.util.*;

class Solution {    
    static int[] frontPointers;
    static int[] backPointers;
    static int current;
    static Deque<Integer> stack;
    
    public String solution(int n, int k, String[] cmd) {
        current = k;
        stack = new ArrayDeque<>(); 
        
        // 각 행의 이전 행, 다음 행의 인덱스를 가리키는 포인터 배열
        frontPointers = new int[n];
        backPointers = new int[n];
        for(int i = 0; i < n; i++){
            frontPointers[i] = i - 1;
            backPointers[i] = i + 1;
        }
        frontPointers[0] = -1; // 가장 위 행의 위는 없음
        backPointers[n - 1] = -1; // 가장 아래 행의 아래는 없음
        
        for(String s : cmd){
            StringTokenizer st = new StringTokenizer(s);
            String curr = st.nextToken();
            
            switch(curr){
                case "U":
                    int uNum = Integer.parseInt(st.nextToken());
                    while(uNum--  > 0){
                        current = frontPointers[current];
                    }
                    break;
                case "D":
                    int dNum = Integer.parseInt(st.nextToken());
                    while(dNum--  > 0){
                        current = backPointers[current];
                    }
                    break;
                case "C":
                    delete();
                    break;
                case "Z":
                    recover();
                    break;
            }
        }
        
        // 결과값을 전부 O로 초기화
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            sb.append("O");
        }
        
        // stack에 남아있는 idx들은 삭제되어있는 idx이므로 갱신
        while(!stack.isEmpty()){
            int del = stack.pop();
            
            sb.setCharAt(del, 'X');
        }
        
        return sb.toString();
    }
    
    private void delete(){
        // 현재 행을 스택에 추가
        stack.push(current);
        
        // current는 건드리지 않고, 앞 뒤 행끼리만 연결
        if(frontPointers[current] != -1){
            backPointers[frontPointers[current]] = backPointers[current];
        }
        
        if(backPointers[current] != -1){
            frontPointers[backPointers[current]] = frontPointers[current];    
        }
        
        // 아래 행이 있으면 아래 행으로 마지막 행이라 아래 행이 없으면 위 행으로 이동
        if(backPointers[current] != -1){
            current = backPointers[current];
        } else {
            current = frontPointers[current];
        }
    }
    
    private void recover(){
        int rec = stack.pop();
        
        // 앞 뒤 행을 다시 연결
        if(frontPointers[rec] != -1){
            backPointers[frontPointers[rec]] = rec;
        }
        
        if(backPointers[rec] != -1){
            frontPointers[backPointers[rec]] = rec;
        }
    }
}