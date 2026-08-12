import java.util.ArrayList;
import java.util.List;

class Solution {
    static List<Integer>[] graph;
    static int[] staticInfo;
    static int[] lambState;

    // dfs -> 현재 노드에서 다음 노드 탐색작업, 종료 조건 = 더이상 탐색할 노드 없을 떄
    // 부채는 늑대를 만나면 +1, 양을 만나면 현재 부채값이 양배열 인덱스가 됨
    static void dfs(int node, int cost) {
        if (staticInfo[node] == 0) {
            // 현재 노드가 양이면
            lambState[cost] += 1;
            // 양 발견 시, 필요한 양의 수 배열 인덱스에 값 추가, 리턴 없음
            // 양 만나면 cost변동 없음
        } else {
            // 늑대면
            // cost정보가 관리가 잘되나? -> 인자로 관리하니까 호출 스테이지는 해당 스테이지의 cost를 활용한다.
            if (cost == 0) // 처음 만난 늑대는 2마리는 필요함
                cost = 2;
            else
                cost += 1;
        }
        for (int childNode : graph[node]) {
            dfs(childNode, cost);
        }
    }

    public int solution(int[] info, int[][] edges) {
        int answer = 0;
        // 전체 노드 탐색하면서 양을 발견하면 해당 양을 얻기 위해 필요한 양의 수를 기록
        // int[][] graph info.length, 2
        int n = info.length;
        graph = new ArrayList[n];
        for (List<Integer> g : graph)
            g = new ArrayList<>();

        staticInfo = info;
        lambState = new int[n];

        // edge돌면서 graph 완성
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
        }

        // dfs호출하고 lambState완성하기
        dfs(0, 0);

        // 양이 0마리 필요한 양
        // ... 양이 n마리 필요한 양
        // 0마리 양부터 빌드해서 순차적으로 가질 수 있는 양을 늘리기
        // 0 - 3 (3, 0) -> 3 - 3 -> (4, 2) -> (5, 4) -> (6, 6)
        int[] state = new int[2];
        for (int i = 0; i < n; i++) {
            if (lambState[i] > 0) {
                // i cost의 양의 수가 있을 때
                // 해당 양을 데려올 수 있을 때
                if (state[0] >= i) {
                    while (state[0] - i > 0) {
                        state[0] += 1;
                        state[1] += (i - 1);
                        // 데려왔더니 늑대, 양이 동률일 때, 양 한마리 빼기
                        if (state[1] >= state[0])
                            return state[0] - 1;
                        lambState[i] -= 1;
                    }
                }
            }
        }
        return answer;
    }
}
