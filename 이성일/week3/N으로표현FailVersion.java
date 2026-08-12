
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {

    // 1. N의 개수에 따라 만들 수 있는 수 리스트 관리
    // 1.1 N의 개수는 최대 8
    static Set<Integer>[] list = new HashSet[9];
    // 1.2 number 사이즈의 N의 개수를 저장하는 배열 생성
    static int[] nums = new int[32001];

    static boolean amplify(int left, int right, int len, int number) {

        for (int n : list[left]) {
            for (int m : list[right]) {
                int[] ops = { n + m, Math.abs(n - m), n * m, (Math.max(n, m) / Math.min(n, m)) };
                for (int opResult : ops) {
                    if (opResult > 32000 || opResult <= 0)
                        continue;
                    if (opResult == number) {
                        nums[number] = left + right;
                        return true;
                    }
                    if (nums[opResult] < left + right)
                        continue;
                    nums[opResult] = left + right;
                    list[len].add(opResult);
                }
            }
        }

        return false;
    }

    public int solution(int N, int number) {
        int answer = -1;
        for (int i = 0; i < list.length; i++) {
            list[i] = new HashSet<>();
        }
        list[1].add(N);
        Arrays.fill(nums, Integer.MAX_VALUE);
        nums[N] = 1;

        // 2. 리스트 빌드
        // 2.1 2개부터 시작, 이전 개수들의 조합으로 리스트 관리
        int len = 2;
        while (len < 9) {
            int left = 1, right = len - 1;
            boolean flag = false;
            int concatNum = 0;
            for (int i = 0; i < len; i++) {
                concatNum += N * (int) (Math.pow(10, i));
            }
            list[len].add(concatNum);
            // 2.2 이중루프를 돌며 이전 개수 조합간의 새로운 수 생성
            // 2.2.1 더하기 빼기 곱하기 나누기 숫자 이어붙이기는 마지막에
            while (left <= right) {
                if (amplify(left, right, len, number)) {
                    flag = true;
                    break;
                }
                left++;
                right--;
            }
            if (flag) {
                answer = nums[number];
                break;
            }
        }
        // 2.3 새로운 수가 number이면 answer값 저장 및 종료
        return answer;
    }
}
