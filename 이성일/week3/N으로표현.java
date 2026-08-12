import java.util.HashSet;
import java.util.Set;

class Solution {

    static Set<Integer>[] dp = new HashSet[9];

    static void amplify(int left, int right, int len, int number) {

        for (int n : dp[left]) {
            for (int m : dp[right]) {
                int[] ops = { n + m, Math.abs(n - m), n * m, (Math.max(n, m) / Math.min(n, m)) };
                for (int opResult : ops) {
                    if (opResult > 32000 || opResult <= 0)
                        continue;
                    dp[len].add(opResult);
                }
            }
        }

    }

    public int solution(int N, int number) {
        int answer = -1;
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new HashSet<>();
        }
        dp[1].add(N);

        int len = 1;
        while (len < 9) {
            int left = 1, right = len - 1;

            int concatNum = 0;
            for (int i = 0; i < len; i++) {
                concatNum += N * (int) (Math.pow(10, i));
            }
            dp[len].add(concatNum);

            while (left <= right) {
                amplify(left, right, len, number);
                left++;
                right--;
            }

            if (dp[len].contains(number)) {
                return len;
            }
            len++;
        }
        // 2.3 새로운 수가 number이면 answer값 저장 및 종료
        return answer;
    }
}
