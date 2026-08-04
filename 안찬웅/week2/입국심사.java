import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        
        Arrays.sort(times);
        
        long time = 1;
        long high = (long) times[times.length-1] * n;
        long low = 0;
        long sum;
        
        while(low <= high) {
            long mid = (low + high) / 2;
            sum = 0;
            
            for(int x : times) {
                sum += mid / x;
            }
            
            if(sum >= n) {
                high = mid - 1;
                time = mid;
            } else {
                low = mid + 1;
            }
        }
        return time;
    }
}
