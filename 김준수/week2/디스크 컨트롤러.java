/*
로직
- 우선순위 큐
- 각 초마다 우선순위 큐에서 우선순위대로 뽑으면서 실행 가능한지 검사
- 실행 가능하면 소요시간만큼 초 지나고 결과 리스트에 저장
- 실행 불가능하면 다시 작업들을 우선순위에 넣고 다음 초로 넘어가서 다시 진행
- 최초 실행 시간을 모든 작업에서 가장 작은 호출 시간으로 시작하면 무의미한 스킵을 막을 수 있음
*/

import java.util.*;

class Solution {
    class Task implements Comparable<Task>{
        int taskNum;
        int call;
        int spend;
        int ta;
        
        Task(int t, int c, int s){
            taskNum = t;
            call = c;
            spend = s;
            ta = 0;
        }
        
        @Override
        public int compareTo(Task t){
            if(spend < t.spend){
                return -1;
            }
            else if(spend > t.spend){
                return 1;
            }
            else{
                if(call < t.call){
                    return -1;
                }
                else if(call > t.call){
                    return 1;
                }
                else{
                    if(taskNum < t.taskNum){
                        return -1;
                    }
                    else if(taskNum > t.taskNum){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
            }
        }
    }
    
    public int solution(int[][] jobs) {
        PriorityQueue<Task> pq = new PriorityQueue<>();
        List<Task> resultList = new ArrayList<>();
        
        for(int i = 0; i < jobs.length; i++){
            pq.offer(new Task(i, jobs[i][0], jobs[i][1]));
        }
        
        int time = 0;
        
        while(!pq.isEmpty()){
            Task curr = pq.poll();
            
            // 만약 curr이 아직 호출되지 않았다면 따로 빼서 다시 큐로
            List<Task> notYet = new ArrayList<>();
            while(curr.call > time){
                // 모든 작업이 현 시점에 호출 없으면 다음 초로
                if(pq.isEmpty()){
                    notYet.add(curr);
                    curr = null;
                    break;
                }
                
                notYet.add(curr);
                curr = pq.poll();
            }
            for(Task t : notYet){
                pq.offer(t);
            }
            if(curr == null){
                time++;
                continue;
            }
            
            time += curr.spend;
            curr.ta = time - curr.call;
            resultList.add(curr);
        }
        
        // 반환 시간 평균 계산
        int answer = 0;
        for(Task t : resultList){
            answer += t.ta;
        }
        return answer / resultList.size();
    }
}
