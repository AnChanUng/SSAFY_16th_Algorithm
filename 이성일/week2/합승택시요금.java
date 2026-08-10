import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {

    public static class Node {
        int idx;
        int cost;

        public Node(int idx, int cost) {
            this.idx = idx;
            this.cost = cost;
        }
    }

    public static int[] djikstra(int n, int s, int[][] imap) {
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        pq.offer(new Node(s, 0));
        boolean[] visited = new boolean[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            visited[node.idx] = true;
            // 1-4-1 해당 노드로부터 인접 노드 구하고 비용 갱신 (미방문 && 최적값 존재) 가능 시, pq에 offer한다.
            for (int j = 1; j < n + 1; j++) {
                if (imap[node.idx][j] != 0) {
                    if (!visited[j] && ((imap[node.idx][j] + dist[node.idx]) <= dist[j])) {
                        // 갱신
                        // 1-4-1-1 비용 갱신 시, 경로를 기록한다.
                        dist[j] = imap[node.idx][j] + dist[node.idx];
                        pq.offer(new Node(j, dist[j]));
                    }
                }
            }
        }
        return dist;
    }

    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = 0;
        // 1. 다익스트라 구성
        int[][] imap = new int[n + 1][n + 1];

        for (int[] f : fares) {
            imap[f[0]][f[1]] = f[2];
            imap[f[1]][f[0]] = f[2];
        }

        int[] dist = djikstra(n, s, imap);
        int[] distA = djikstra(n, a, imap);
        int[] distB = djikstra(n, b, imap);

        // 2. s에서 dest1,dest2 까지 가는 최저비용 구하기
        answer = Integer.MAX_VALUE;
        for (int k = 1; k <= n; k++) {
            // 공유 노드 후보
            if ((dist[k] != Integer.MAX_VALUE) && (distA[k] != Integer.MAX_VALUE) && (distB[k] != Integer.MAX_VALUE))
                answer = Math.min(answer, distA[k] + distB[k] + dist[k]);
        }
        // 3. 두 경로 사이에 시작부터 중복 경로 구하고 해당 경로 비용 빼기
        return answer;
    }
}
