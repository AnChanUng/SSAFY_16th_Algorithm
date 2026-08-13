/*
전략
- DP
- dp[i] : i를 만드는데 필요한 최소한의 N 사용 횟수
    - dp[N], dp[NN], dp[NNN], dp[NNNN], dp[NNNNN]으로 시작
- BFS처럼 가능한 숫자들을 큐에 넣고, 하나씩 꺼내서 다음 가능한 경우의 수들을 만들어서 큐에 넣음
    - N, NN, NNN, NNNN, NNNNN 을 사칙연산 가능. 이 경우 N의 개수만큼 count 추가
    - 1, 11, 111, 1111, 11111을 사칙연산 가능. 이 경우 1의 개수 + 1만큼 count 추가
        - N / N = 1 ⇒ count : 2
        - NN / N = 11 ⇒ count : 3
    - 총 10개의 숫자를 사칙연산하므로 하나의 숫자 당 40개의 숫자를 추가 가능
*/

import java.util.*;

class Solution {    
    public int solution(int N, int number) {
        int[] dp = new int[32001];
        for(int i = 0; i < 32001; i++){
            dp[i] = 9; // count의 최대치 8
        }
        
        int NN = Integer.parseInt(String.format("%d%d", N, N));
        int NNN = Integer.parseInt(String.format("%d%d%d", N, N, N));
        int NNNN = Integer.parseInt(String.format("%d%d%d%d", N, N, N, N));
        int NNNNN = Integer.parseInt(String.format("%d%d%d%d", N, N, N, N));
        int[] NList = new int[]{N, NN, NNN, NNNN, NNNNN};
        int[] oneList = new int[]{1, 11, 111, 1111, 11111};
            
        Queue<int[]> queue = new ArrayDeque<>();
        for(int num : NList){
            // (int) Math.log10(num) + 1 : num의 자릿수 계산 방법
            queue.offer(new int[]{num, (int) Math.log10(num) + 1});
        }
        
        
        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int num = curr[0];
            int count = curr[1];
            
            // 스킵 조건
            if(count > 8 || num > 32000 || num <= 0){
                continue;
            }
            
            // 더 작은 쪽으로 갱신
            dp[num] = Math.min(dp[num], count);
            
            // N 연산
            for(int next : NList){
                queue.offer(new int[]{num + next, count + (int) Math.log10(next) + 1});
                queue.offer(new int[]{num - next, count + (int) Math.log10(next) + 1});
                queue.offer(new int[]{num * next, count + (int) Math.log10(next) + 1});
                queue.offer(new int[]{num / next, count + (int) Math.log10(next) + 1});
            }
            
            // 1 연산
            for(int next : oneList){
                queue.offer(new int[]{num + next, count + (int) Math.log10(next) + 2});
                queue.offer(new int[]{num - next, count + (int) Math.log10(next) + 2});
                queue.offer(new int[]{num * next, count + (int) Math.log10(next) + 2});
                queue.offer(new int[]{num / next, count + (int) Math.log10(next) + 2});
            }
        }
        
        if(dp[number] == 9){
            return -1;
        }
        else{
            return dp[number];
        }
    }
}