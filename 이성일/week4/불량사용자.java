import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

class Solution {

    static int answer;
    
    static Set<Set<Integer>> lists;
    
    static boolean[] visited;

    static int userSize;

    static int nodeSize;
    
    // 응모자 아이디 컬렉트 dfs 함수
     
        // 제재 아이디 순으로 dfs 진행, 다음 아이디가 다음 스테이지
        // 스테이지별로 응모자아이디와 같은지 비교하기
            // 아이디 비교 시 길이가 다르면 비교 x
            // 어순으로 비교 , *은 pass 어순 비교 시 다르면 다른 문자 취급
            // 모든 어순 같으면 같은 문자
            // 같은 문자 발생하면 (미방문 일 시) 조합에 담고 찾은 조합이면 pass
                // visited, set set<set> // 방문 이후 관리
    static void dfs(int idx, Set<Integer> states, String[] user_id, String[] banned_id) {
        if (idx == nodeSize) {
            // 모든 노드가 pair를 찾은 경우
            for (Set<Integer> candidate: lists) {
                if (candidate.equals(states)) {
                    return;
                }
            }
            
            Set<Integer> candidate = new HashSet<>();
            candidate.addAll(states);
            lists.add(candidate);
            return;
        }

        for (int i = 0; i < userSize; i++) {
            if (visited[i]) continue;
            int charPointer = 0;
            boolean flag = true;

            if (user_id[i].length() != banned_id[idx].length()) continue;
            while (charPointer < banned_id[idx].length()) {
                if (banned_id[idx].charAt(charPointer) == '*') {
                    charPointer++;
                    continue;
                }
                if (banned_id[idx].charAt(charPointer) != user_id[i].charAt(charPointer)) {
                    flag = false;
                    break;
                }
                charPointer++;
            }

            if (flag && !visited[i]) {
                states.add(i);
                visited[i] = true;
                dfs(idx+1, states, user_id, banned_id);
                visited[i] = false;
                states.remove(i);
            }
        }




    }
    
    public int solution(String[] user_id, String[] banned_id) {
        answer = 0;
        userSize = user_id.length;
        nodeSize = banned_id.length;
        lists = new HashSet<>();

        visited = new boolean[user_id.length];
        // 제재 아이디 별로 한명의 응모자를 매핑한 조합의 개수를 센다.
        Set<Integer> states = new HashSet<>();
        dfs(0, states, user_id, banned_id);

        answer = lists.size();

        return answer;
    }
}
