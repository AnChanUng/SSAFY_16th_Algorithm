import java.util.Arrays;
/**
* 시행착오
1. 문제 조건을 명확히 인지 하지 못했음
    1-1. 시간을 심사위원 소요 시간별로 나누어서 더하는 게 n값인데
    1-2. 기다렸다가 대기한다는 상황제시를 무작위적인 배치로 생각했고
    1-3 최대 소요 심사위원이 있다는 생각으로 빠지게되었음
    1-4 샘플 케이스를 명확하게 시뮬레이션하는 게 좋겠음
2. gpt 힌트를 받아서, 탐색 변수를 시간으로 설정했음
    2-1. 최소 시간이 아님에도 인원은 충족할 수 있음
    2-2. 단지, 탐색 시 만족하는 값이 있다면 일단 값을 저장 후, 더 탐색하면서 갱신하면 되는데
    2-3. 코드 템플릿을 생각하고 구현을 어려워했음
3. 구현력이 떨어진다
    3-1. 템플릿에 의존적임
    3-2. 사고를 코드로 구현하는 연습이 필요함
*/

class Solution {
    
    public static long people;
    public static long answer;
    
    public static void binarySearch(long minTime, long maxTime, int[] times){
        while (minTime <= maxTime) {
            long midTime = (minTime+maxTime) / 2l;
            long cnt = 0;
            for (int t: times){
                cnt += (midTime) / (long) t;
            }
            if (cnt >= people) {
                // 시간이 대기인원에 비해 많다.
                // 일단 정답 후보지만, t = 9, times = [2,4]  -> t = 8가 최적인 반례가 있다.
                // 따라서, 정답으로 정해두고, 한번 더 탐색한다.
                answer = midTime;
                maxTime = midTime - 1l;
            } else {
                // 시간이 대기 인원에 비해 부족하다
                minTime = midTime + 1l;
            }
        }
    }
    
    public long solution(int n, int[] times) {
        int judges = times.length;
        Arrays.sort(times);
        long longestTime = times[judges-1];
        long worstTime = longestTime * (long) n;
        people = n;
        binarySearch(1, worstTime, times);
        
        return answer;
    }
}