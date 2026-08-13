import java.util.ArrayList;
import java.util.List;

class Solution {
    static List<Integer>[] graph;
    static int answer;

    static void dfs(int sheep, int wolf, List<Integer> candidates, int[] info) {
        if (sheep > answer)
            answer = sheep;
        for (int candidate : candidates) {
            List<Integer> newCandidates = new ArrayList<>(candidates);
            newCandidates.remove(Integer.valueOf(candidate));
            newCandidates.addAll(graph[candidate]);
            if (info[candidate] == 0) {
                dfs(sheep + 1, wolf, newCandidates, info);

            } else {
                if (wolf + 1 >= sheep)
                    continue;
                dfs(sheep, wolf + 1, newCandidates, info);
            }
        }
    }

    public int solution(int[] info, int[][] edges) {
        int n = info.length;
        answer = 0;
        graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // edge돌면서 graph 완성
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
        }

        dfs(1, 0, graph[0], info);

        return answer;
    }
}
