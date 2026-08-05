package codetest;

import java.util.PriorityQueue;

class Solution {
    
    public static int answer = 0;
    
    public int solution(int[][] jobs) {
        
        // 시간에 도달해야 작업을 처리할 수 있다
            // 가능한 작업 현시간과 동일 또는 이전에 들어온 작업 중 가장 우선순위가 높은 작업
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1,o2) -> {
            return (o1[0] == o2[0]) ? Integer.compare(o1[1], o2[1]) : Integer.compare(o1[0], o2[0]);
        });
        
        for (int[] j : jobs){
            pq.offer(j);
        }
        
        
        int t = 0;
        int turnAround = 0;
        while (!pq.isEmpty()){
            int[] v = pq.poll();
            if (v[0] > t) {
                // 미래에 시작할 수 있는 작업
                pq.offer(v);
                // 다시 넣기
                t = v[0];
                // 시간 초기화 하기
            } else if (v[0] < t) {
                // 묵혀있던 작업
                // 시간 최신화 시키고 다시 비교
                pq.offer(new int[]{t, v[1]});
                turnAround += (t - v[0]);
            }
            else {
                t += v[1];
                turnAround += (t - v[0]);
            }
        }
        
        answer = turnAround /jobs.length;
        
        
        return answer;
    }
}