import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int solution(int N, int number) {
        int answer = 0;
        // 1. N의 개수에 따라 만들 수 있는 수 리스트 관리
        List<Integer>[] list = new ArrayList[9];
        list[1].add(N);
            // 1.1 N의 개수는 최대 8
            // 1.2 number 사이즈의 N의 개수를 저장하는 배열 생성
        int[] nums = new int[32001];
        Arrays.fill(nums, Integer.MAX_VALUE);
        nums[N] = 1;
        // 2. 리스트 빌드
            // 2.1 2개부터 시작, 이전 개수들의 조합으로 리스트 관리
        int len = 2;
        while (len < 9) {
            
        }
            // 2.2 이중루프를 돌며 이전 개수 조합간의 새로운 수 생성
                // 2.2.1 더하기 빼기 곱하기 나누기 숫자 이어붙이기는 마지막에
            // 2.3 새로운 수가 number이면 answer값 저장 및 종료
        return answer;
    }
}