/*
전략
- 매개변수 탐색
- 시간이 T, 각 심사관의 시간이 a, b일때 각 심사관이 해당 시간에 담당할 수 있는 사람 수는 N / a, N / b
- 시간을 매개변수로 두고 탐색해서, 특정 시간 T일 때 각 심사관이 담당할 수 있는 사람의 수가 n 이상이면 입국심사 가능 = isValid
- 입국심사 가능이면 더 작은 시간을 시도, 입국심사 불가능이면 더 큰 시간을 시도
- 시간이 가장 작은 경계를 찾기 위해 start ≤ end일때까지 반복 후 start가 결과값

시행착오
- end의 최적 값은 가장 오래 걸리는 심사관이 모든 사람을 담당하는 것인데, 그 과정에서 times 배열이 int인데 long인 end에 저장하려고 하니 오버플로우가 발생했음. 명시적 형변환을 추가해서 해결
*/

import java.util.*;

class Solution {
    static int n;
    static int[] times;
    
    public long solution(int n, int[] times) {
        this.n = n;
        this.times = times;
        long answer = 0;
        
        Arrays.sort(times);
        long start = 0;
        // times가 int[] 형이라 명시적 형변환 하지 않으면 오버플로우 발생 
        // 가능한 최대 시간 = 가장 오래 걸리는 심사관이 n명을 모두 담당
        long end = (long) times[times.length - 1] * n;
        long mid = 0;
        
        while(start <= end){
            mid = (start + end) / 2;
            
            // 입국 심사가 가능하면 더 작은 시간을 시도
            if(isValid(mid)){
                end = mid - 1;
            }
            // 입국 심사가 불가능하면 더 큰 시간을 시도
            else{
                start = mid + 1;
            }
        }
        
        return start;
    }
    

    private boolean isValid(long time){
        long result = 0;
				
				// 각 심사관이 time 내에 담당할 수 있는 사람의 수를 더함        
        for(int t : times){
            result += time / t;
        }
        
        // 전체 심사관이 담당할 수 있는 사람 수가 n 이상이면 통과
        if(result >= n){
            return true;
        }
        else{
            return false;
        }
    }
}
