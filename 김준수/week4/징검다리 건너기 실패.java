/*
실패 코드
- 정확성 테스트는 다 맞는데 효율성 테스트 3/14
- 3개는 통과했으니까 매개변수 탐색이라는 접근이 틀린 건 아닌듯
- isValid의 시간 복잡도를 더 줄여야 할 것 같음
*/
import java.util.*;

class Solution {
    static int[] stones;
    static int k;
    
    public int solution(int[] stones, int k) {
        this.stones = stones;
        this.k = k;
        
        // 돌이 1개면 해당 돌의 수를 리턴
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
            
            //System.out.println(start + " " + mid + " " + end);
            
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
        
        // 사람 수만큼 디딤돌 수 빼기
        for(int i = 0; i < nextStones.length; i++){
            nextStones[i] -= people;
        }
        
        // 현재 돌에서 점프가 가능한지 판별
        // 마지막 돌은 살아서 갈 수만 있으면 무조건 통과니까 스킵
        for(int i = 0; i < nextStones.length - 1; i++){
            // 다음 돌이 0 이하면 판별 시작
            if(nextStones[i + 1] <= 0){
                for(int j = 1; j <= k; j++){
                    int nextIdx = i + j;
                    
                    //System.out.println(i + "번째 돌의 다음 " +  j + "번째 돌 검증 중");
                    // 배열 범위 넘으면 판별 종료
                    if(nextIdx >= nextStones.length){
                        //System.out.println(i + "번째 돌의 다음 " + j + "번째 돌 검증 통과(범위초과)");
                        break;
                    }
                    
                    // 종료 조건 : k번째 다음 돌까지 전부 0 이하면 false
                    if(j == k && nextStones[nextIdx] <= 0){
                        //System.out.println(i + "번째 돌의 다음 " + j + "번째 돌 최종 검증 실패");
                        return false;
                    }
                    
                    // 밟을 수 있는 돌이 있으면 판별 종료
                    if(nextStones[nextIdx] > 0){
                        //System.out.println(i + "번째 돌의 다음 " + j + "번째 돌 검증 통과(밟을 수 있음)" + nextStones[nextIdx]);
                        break;
                    }
                    
                    //System.out.println(i + "번째 돌의 다음 " + j + "번째 돌 검증 실패");
                }
            }
        }
        
        return true;
    }
}