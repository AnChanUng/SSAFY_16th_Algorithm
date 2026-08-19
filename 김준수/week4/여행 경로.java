/*
전략
- 백트래킹
- 도시 이름을 담은 String 배열 생성. 해당 배열의 인덱스로 도시 구분(String 비교는 너무 비용이 크니)
- 위 배열을 활용해 tickets 배열을 String에서 int로 바꿔서 배열 생성. ⇒ List<List<int[]> graph 생성
    - graph는 출발지 → [도착지, 사용가능여부(0/1] 로 저장
    - graph 생성 완료 후 각 요소를 정렬해줘야 알파벳 순서 return 가능
- ICN의 인덱스 찾아서 dfs 시작
    - 경로 리스트 List<Integer>를 파라미터로 받아서 관리
- 경로 리스트를 int에서 String으로 변환해서 반환
*/

import java.util.*;

class Solution {
    static List<String> cities;
    static int[][] ticket;
    static List<List<int[]>> graph;
    static List<Integer> result;
    
    public String[] solution(String[][] tickets) {
        result = null;
        cities = new ArrayList<>();
        for(String[] t : tickets){
            if(!cities.contains(t[0])){
                cities.add(t[0]);
            }
            if(!cities.contains(t[1])){
                cities.add(t[1]);
            }
        }
        Collections.sort(cities);
    
        // tickets를 int로 변환한 ticket
        ticket = new int[tickets.length][2];
        for(int i = 0; i < tickets.length; i++){
            ticket[i][0] = cities.indexOf(tickets[i][0]);
            ticket[i][1] = cities.indexOf(tickets[i][1]);
        }
        
        graph = new ArrayList<>();
        for(int i = 0; i < cities.size(); i++){
            graph.add(new ArrayList<>());
        }
        for(int[] t : ticket){
            // 출발지 -> [도착지, 사용가능여부(0/1)]
            graph.get(t[0]).add(new int[]{t[1], 1});
        }
        // 그래프 정렬(알파벳 순으로 먼저 가기 위해)
        for(int i = 0; i < graph.size(); i++){
            graph.get(i).sort((a, b) -> a[0] - b[0]);
        }
        
        dfs(0, cities.indexOf("ICN"), new ArrayList<>(Arrays.asList(cities.indexOf("ICN"))));
        
        String[] answer = new String[result.size()];
        for(int i = 0; i < result.size(); i++){
            answer[i] = cities.get(result.get(i));
        }
        
        return answer;
    }
    
    private void dfs(int depth, int curr, List<Integer> course){
        // 종료 조건 : 모든 티켓 다 썼을 때
        if(depth == ticket.length){
            if(result == null){
                // 깊은 복사
                result = new ArrayList<>();
                for(int c : course){
                    result.add(c);
                }
            }
        }
        
        for(int i = 0; i < graph.get(curr).size(); i++){
            int[] next = graph.get(curr).get(i);
            
            if(next[1] != 1){
                continue;
            }
            
            next[1] = 0;
            course.add(next[0]);
            
            dfs(depth + 1, next[0], course);
            
            next[1] = 1;
            course.remove(course.size() - 1);
        }
    }
}