// import java.util.ArrayDeque;
// import java.util.Arrays;

// class Solution {

// static int[] dx = {-1, 0, 1, 0};
// static int[] dy = {0, 1, 0, -1};
// static int[][][] costs;

// static class Road {
// int r;
// int c;
// int dir;
// int cost;
// Road(int r, int c, int dir) {
// this.r = r;
// this.c = c;
// this.dir = dir;
// }
// }

// static boolean inRange(int size, int x, int y) {
// return ((x>=0 && x < size) && (y >= 0 && y < size)) ? true : false;
// }

// public int solution(int[][] board) {
// int answer = Integer.MAX_VALUE;

// int size = board.length;
// costs = new int[4][size][size];
// for (int[][] cost : costs) {
// for (int[] c: cost) Arrays.fill(c,Integer.MAX_VALUE);
// }

// ArrayDeque<Road> deq = new ArrayDeque<>();
// for (int i = 0; i < 2; i++) {

// }
// Road start = new Road(0, 0, 0);

// start.cost = 0;

// for (int i = 0; i < 4; i++) {
// costs[i][0][0] = start.cost;
// }
// deq.add(start);

// while (!deq.isEmpty()) {
// Road road = deq.poll();
// // 종료 조건: n-1, n-1 칸에 도달했을 경우
// if (road.r == (size-1) && road.c == (size-1)) {
// answer = Math.min(answer, road.cost);
// continue;
// }

// for (int i = 0; i < dx.length; i++){
// int newR = road.r + dx[i], newC = road.c + dy[i];
// // if (!inRange(size, newR, newC) || visited[i][newR][newC] ||
// (board[newR][newC] == 1)) continue;
// if (!inRange(size, newR, newC) || (board[newR][newC] == 1)) continue;
// Road newRoad = new Road(newR, newC, i);
// if (road.r == 0 && road.c == 0) {
// newRoad.cost = 100;
// deq.add(newRoad);
// costs[newR][newC] = newRoad.cost;
// continue;
// }
// if (road.dir == newRoad.dir) newRoad.cost = road.cost + 100;
// else newRoad.cost = road.cost + 600;

// if (newRoad.cost > costs[newR][newC]) continue;

// deq.add(newRoad);
// costs[newR][newC] = newRoad.cost;

// }
// }

// return answer;
// }
// }
