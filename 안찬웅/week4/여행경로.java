import java.util.*;
/*
    ICN에서 출발해서 여행경로 짜기
    
    알고리즘: DFS 조합 백트래킹
    
    ICN에서 출발해서 tickets[0][1]로 이동 -> tickets[i][0]로 계속 이동하면서 record 배열에 여행경로 저장
*/
class Solution {
    static String[] order;
    static boolean[] vis;
    static String[] res;
    public String[] solution(String[][] tickets) {
        order = new String[tickets.length+1];
        vis = new boolean[tickets.length];
        
        Arrays.sort(tickets, (a, b) -> a[1].compareTo(b[1]));

        order[0] = "ICN";
        dfs("ICN", tickets, 1);
        
        return res;
    }
    static void dfs(String start, String[][] tickets, int depth) {
        if(depth == tickets.length + 1) {
            res = order;
            return;
        }
        
        for(int i=0; i<tickets.length; i++) {
            if(res != null) return;
            if(!vis[i]) {
                vis[i] = true;
                if(tickets[i][0].equals(start)) {
                    order[depth] = tickets[i][1];
                    dfs(tickets[i][1], tickets, depth+1);
                }
                vis[i] = false;
            }
        }
    }
}
