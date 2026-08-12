
/*
전략
- 시뮬레이션
- 각 버스에 대해, 버스에 탄 사람의 시각을 담은 2차원 리스트 초기화
    - 우선순위 큐로 먼저 도착한 사람이 버스 탈 수 있게 함
- 결과값은 마지막 버스의 상태만 보면 됨
    - 마지막 버스에 자리가 있으면 버스 도착 시간에 줄 서면 됨
    - 마지막 버스에 자리가 없으면 마지막에 탄 사람보다 1분 빠르면 됨
- 출력 시 fString 사용
*/
import java.util.*;

class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        List<List<Integer>> people = new ArrayList<>(); // 각 셔틀에 탄 사람의 시간
        for(int i = 0; i < n; i++){
            people.add(new ArrayList<>());
        }
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(String time : timetable){
            pq.offer(timeToNum(time));
        }
        
        for(int num = 0; num < n; num++){
            int time = 540 + t * num; // 이번 셔틀의 도착 시간
            Queue<Integer> left = new ArrayDeque<>();
            
            while(!pq.isEmpty()){
                int curr = pq.poll();
                
                // 시간, 자리 상 탈 수 있는 경우 타기
                if(curr <= time && people.get(num).size() < m){
                    people.get(num).add(curr);
                }
                // 못탄 경우 다시 추가
                else{
                    left.offer(curr);
                }
            }
            
            // 남은 사람들을 다시 pq에 추가
            while(!left.isEmpty()){
                pq.offer(left.poll());
            }
            
        }        
        
        int result = -1;
        // 맨 마지막 차에 자리가 있으면 도착 시간에 줄서면 됨
        if(people.get(n - 1).size() < m){
            result = 540 + (n - 1) * t;
        }
        // 맨 마지막 차에 자리가 없으면 마지막에 탄 사람보다 1분 빠르면 됨
        else{
            result = people.get(n - 1).get(people.get(n - 1).size() - 1) - 1;
        }
        
        return String.format("%02d:%02d", result / 60, result % 60);
    }
    
    private int timeToNum(String time){
        return Integer.parseInt(time.substring(0, 2)) * 60 
            + Integer.parseInt(time.substring(3, 5));
    }
}