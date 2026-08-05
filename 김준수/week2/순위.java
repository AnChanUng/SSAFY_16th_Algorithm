/*
전략
- 키순서와 동일한 문제
- 모든 정점에 대해, 나보다 강한 사람의 수 찾기
- 모든 정점에 대해, 나보다 약한 사람의 수 찾기
- 강한 사람 + 약한 사람 == N - 1이면 자신의 순위를 알 수 있음
*/
import java.util.*;

class Solution {
    static List<List<Integer>> strongs; // i보다 센 사람
    static List<List<Integer>> weaks; // i보다 약한 사람
    
    public int solution(int n, int[][] results) {
        int answer = 0;
        strongs = new ArrayList<>();
        weaks = new ArrayList<>();
        
        for(int i = 0; i <= n; i++){
            strongs.add(new ArrayList<>());
            weaks.add(new ArrayList<>());
        }
        
        for(int[] r : results){
            strongs.get(r[1]).add(r[0]);
            weaks.get(r[0]).add(r[1]);
        }
        
        for(int i = 1; i <= n; i++){
            int cnt = countStrong(i);
            cnt += countWeak(i);
            
            // 위 아래 사람 수가 n - 1이면 자기 순위 알 수 있음
            if(cnt == n - 1){
                answer++;
            }
        }
        
        return answer;
    }
    
    // i보다 센 사람의 수 세기
    private int countStrong(int i){
        int result = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> set = new HashSet<>(); // 중복 방지
        queue.offer(i);
        set.add(i);
        
        while(!queue.isEmpty()){
            int curr = queue.poll();
            
            for(int s : strongs.get(curr)){
                if(!set.contains(s)){
                    result++;
                    queue.offer(s);
                    set.add(s);
                }
            }
        }
        
        return result;
    }
    
    // i보다 약한 사람의 수 세기
    private int countWeak(int i){
        int result = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> set = new HashSet<>(); // 중복 방지
        queue.offer(i);
        set.add(i);
        
        while(!queue.isEmpty()){
            int curr = queue.poll();
            
            for(int w : weaks.get(curr)){
                if(!set.contains(w)){
                    result++;
                    queue.offer(w);
                    set.add(w);
                }
            }
        }
        
        return result;
    }
}
