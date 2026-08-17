/*
전략
- 매개변수 탐색
    - 매개변수 : 징검다리를 건널 수 있는 사람의 수
        - 가능한 사람수 / 불가능한 사람수 의 경계가 되는 가능한 사람 수를 찾아야 함
        - 최근에 풀어보니 매개변수 탐색의 매개변수는 정답으로 제시해되는 변수인 경우가 많은듯
    - isValid : (현재 사람 수)가 징검다리를 건넜을 때 다음 사람이 징검다리를 건널 수 있는가?
        - 징검다리의 돌에 적힌 수를 전부 현재 사람 수만큼 빼줘서 isValid에 맞는 상태를 만듬
        - 처음부터 끝까지 징검다리를 돌면서 연속된 0의 개수를 찾음
        - 연속된 0의 개수가 k보다 같거나 크면 false

시행착오
- isValid의 로직을 시뮬레이션 형식으로 짰더니 시간초과 발생
    - 처음부터 끝까지 징검다리를 돌면서 다음 돌이 0 이하면 검증 시작
    - 다음 1번째 돌부터 k번째 돌까지 전부 돌면서 하나라도 1 이상이 있으면 검증 중지
    - k번째 돌까지 다 돌았는데 모두 0 이하였으면 false 반환
    - 모든 돌을 다 돌았는데 검증 완료됐으면 true 반환
*/

import java.util.*;

class Solution {
    static int[] stones;
    static int k;
    
    public int solution(int[] stones, int k) {
        this.stones = stones;
        this.k = k;
        
        // 돌이 1개면 해당 돌의 수가 정답
        if(stones.length == 1){
            return stones[0];
        }
        
        // 돌 숫자 최대치 찾기
        int end = 0;
        for(int i = 0; i < stones.length; i++){
            end = Math.max(end, stones[i]);
        }
        
        // 매개 변수 탐색(건널 수 있는 사람의 수)
        int start = 0;
        while(start <= end){
            int mid = (start + end) / 2;
            
            if(isValid(mid) == true){
                start = mid + 1;
            }
            else{
                end = mid - 1;
            }
        }
        
        return start;
    }
    
    private boolean isValid(int people){
        int[] nextStones = Arrays.copyOf(stones, stones.length);
        
        // 연속된 0의 개수 찾기
        int zeroCnt = 0;
        int zeroSeqCnt = 0;
        
        // 사람 수만큼 디딤돌 수 빼면서 0 탐색
        for(int i = 0; i < nextStones.length; i++){
            nextStones[i] -= people;
            
            if(nextStones[i] <= 0){
                zeroCnt++;
            }
            else{
                zeroSeqCnt = Math.max(zeroSeqCnt, zeroCnt);
                zeroCnt = 0;
            }
        }
        zeroSeqCnt = Math.max(zeroSeqCnt, zeroCnt);
        
        if(zeroSeqCnt >= k){
            return false;
        }
        else{
            return true;
        }
    }
}