import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Solution {
    public int solution(int n, int[][] results) {
        int answer = 0;
        // 경기 결과를 읽으며 나보다 쎈 약한 선수 리스트 제작
        List<Node> enroll = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            enroll.add(new Node(i));
        }
        
        for (int[] result: results) {
            enroll.get(result[0] - 1).weaker.add(enroll.get(result[1] - 1));
            enroll.get(result[1] - 1).stronger.add(enroll.get(result[0] -1));
        }
        
        
        
        // 각 리스트별로 나보다 쎈선수 dfs로 구하고 약한 선수 dfs로 구해
            // 전체선수 다파악하기
            // 선수별 리스트 루프 
            // 이미 파악된 선수 제외
            // 새로 파악될 때 마다 count
            // 그 이상의 선수가 없을 때 종료 
        for (int i = 0; i < n; i++){
            int scnt = 0, wcnt = 0;
            boolean[] svisited = new boolean[n];
            scnt = sdfs(enroll.get(i).stronger, svisited);
            boolean[] wvisited = new boolean[n];
            wcnt = wdfs(enroll.get(i).weaker, wvisited);
            // 전체 선수 수 - 1 이면 answer += 1   
            if (scnt + wcnt == (n-1)) {
                answer += 1;
            }
        }
                
        
        return answer;
    }
    
    public static int sdfs(List<Node> list, boolean[] visited){
        int count = 0;
        for (Node n: list) {
            if ( visited[n.idx - 1] ) {
                continue;
            } else {
                visited[n.idx -1] = true;
                count++;
                count += sdfs(n.stronger, visited);
            }
        }
        
        return count;
    }
    
    public static int wdfs(List<Node> list, boolean[] visited){
        int count = 0;
        for (Node n: list) {
            if ( visited[n.idx - 1] ) {
                continue;
            } else {
                visited[n.idx -1] = true;
                count++;
                count += wdfs(n.weaker, visited);
            }
        }
        
        return count;
    }
    
    public static class Node {
        public int idx;
        public List<Node> stronger = new ArrayList<>();
        public List<Node> weaker = new ArrayList<>();
        
        public Node(int idx) {
            this.idx = idx;
        }
    }
    
}