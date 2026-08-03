import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        
        Arrays.sort(times);

        long left = times[0];
        long right = (long) times[0] * n;

        while (left <= right) {
            long mid = (left + right)/ 2;

            long count = 0;

            for (int time : times) {
                count += mid / time;

                if (count >= n) {
                    break;
                }
            }

            if (count >= n) {  // mid 시간 안에 n명을 심사할 수 있음
            
                right = mid - 1;
            } else {
 
                left = mid + 1;
            }
        }

        return left;
    }
}
