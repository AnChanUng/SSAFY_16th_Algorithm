import java.util.*;
class Solution {
    public int solution(int[][] jobs) {
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> (a[1] - b[1]));
        int answer = 0;
        int time = 0; //현재 시간
        int count = 0; //현재까지 처리한 작업 수
        int index = 0;
        
        Arrays.sort(jobs, (a,b) -> a[0] - b[0]); //들어온 시간대로 정렬
        
        while(count < jobs.length){
            
            while (index < jobs.length && jobs[index][0] <= time) {
                pq.offer(jobs[index]);
                index++;
}
            
            if(!pq.isEmpty()){
                int[] current = pq.poll(); //가장 짧은 시간 작업 가져옴
                time += current[1];
                answer += time - current[0];
                count += 1;
            }else{
                time = jobs[index][0]; //작업큐에 없다면 제일 가까운 시간으로 이동
            }
        }
        return answer / jobs.length;
        
    }
}
