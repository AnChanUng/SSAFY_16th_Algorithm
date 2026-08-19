---
platform: programmers
problemId: "67259"
author: chanung
source: 안찬웅/week3/경주로 건설.java
week: 3
compiles: true
verdict: good
tags: [good-complexity, good-readability, magic-number]
complexity:
  time: O(N^2 log N)
  space: O(N^2)
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# 경주로 건설 (programmers/67259) — chanung

## 접근

상태를 `(행, 열, 진입방향)` 3차원으로 잡고 우선순위 큐 다익스트라를 돌린다.
이 문제의 핵심은 **"같은 칸이어도 어느 방향으로 들어왔느냐에 따라 이후 비용이 달라진다"** 는 것인데,
`dist[n][n][4]` 로 방향을 상태에 포함시켜 정확히 짚었다.

시작 노드를 `direction = -1` 로 두고 `cur.direction != -1` 일 때만 커브 비용을 더하는 처리가 깔끔하다.
"첫 이동은 어느 방향이든 커브가 아니다"를 **분기 없이 센티넬 값 하나로 해결**했다 — `good-readability`.

**정답 다익스트라와 무작위 판 400개(n=4~8)를 대조해 불일치 0건이다.**
성능도 n=25 빈 판에서 1ms로, 같은 문제 `김준수` 풀이(445ms)와 큰 차이가 난다. `good-complexity`

## 개선점

### 1. (사소) `100` 과 `500` 이 식 안에 박혀 있다 — `magic-number`

```java
int nCost = cur.cost + 100;
if(cur.direction != -1 && dir != cur.direction) {
    nCost += 500;
}
```

주석에는 "직선도로 개당 100원, 코너 개당 500원"이라고 적어뒀는데 코드에는 숫자만 남았다.
상수로 빼면 주석 없이도 읽힌다.

```java
static final int ROAD = 100, CORNER = 500;
```

### 2. (사소) `dist` 를 `dijkstra` 안에서 만든다

```java
static int[][][] dist;
static void dijkstra(int x, int y, int[][] board) {
    dist = new int[n][n][4];
```

`dist` 는 `static` 필드인데 초기화는 `dijkstra` 안에서 일어나고, 결과는 `solution` 이 읽는다.
지금은 `dijkstra` 가 항상 먼저 호출되니 문제없지만, **세 곳에 흩어진 생명주기**라 따라가기 번거롭다.

`dijkstra` 가 `dist` 를 반환하거나, 아예 최솟값까지 계산해서 `int` 를 반환하면
`static` 필드 자체가 없어진다.

```java
int[][][] dist = dijkstra(board);   // 지역 변수로
```

`minTotal` 도 같은 이유로 `static` 일 필요가 없다. `solution` 안의 지역 변수면 충분하다.
같은 문제 `이승주` 풀이도 같은 구조인데, 이런 static 상태가 초기화 누락으로 이어진 사례가
`N으로 표현` 에 있었다 (`static answer` 미초기화로 두 번째 케이스부터 전부 오답).

### 3. (사소) 큐에서 꺼낼 때 스킵 검사가 없다

```java
Node cur = pq.poll();
for(int dir=0; dir<4; dir++) { ... }
```

같은 상태가 더 비싼 비용으로 큐에 여러 번 들어갈 수 있는데, 꺼낼 때 걸러내지 않는다.
넣을 때 `nCost < dist[nx][ny][dir]` 로 막고 있어서 **정확성에는 문제가 없고**,
실측 1ms라 성능도 문제없다. 다만 관례적으로는 한 줄 넣어두는 자리다.

```java
if (cur.direction >= 0 && cur.cost > dist[cur.x][cur.y][cur.direction]) continue;
```

## 복잡도

- 시간: `O(N^2 log N)` — 상태 수가 `N^2 * 4` 이고 각 상태가 힙에 들어간다. 지배적인 건 힙 연산
- 공간: `O(N^2)` — `dist` 가 `N * N * 4`

## 요약

방향을 상태에 넣는 이 문제의 정석을 정확히 짚었고, 시작 방향을 `-1` 센티넬로 처리한 게 특히 깔끔하다.
400판 대조 오답 0건, n=25에서 1ms. 남은 건 전부 정리 수준 — 상수 이름 붙이기와 불필요한 `static` 제거다.
