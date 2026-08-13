import java.util.*;

class Solution {
    
    static int answer = Integer.MAX_VALUE;
    static int N;
    static int target;

    public int solution(int N, int number) {
        this.N = N;
        this.target = number;

        dfs(0, 0);

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

static void dfs(int cnt, int value) {
    

        if (value == target) {
          
            answer = Math.min(answer, cnt);
            return;
        }

        int num = 0;

        for (int i = 1; cnt + i <= 8; i++) {
            
            num = num * 10 + N;

            dfs(cnt + i, value + num);
            
            dfs(cnt + i, value - num);
            
            dfs(cnt + i, value * num);

            if (num != 0) {
                dfs(cnt + i, value / num);
            }
            
        }
    }
}
