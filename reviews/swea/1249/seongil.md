---
platform: swea
problemId: "1249"
author: seongil
source: 이성일/week1/SWEA1249.java
week: 1
compiles: true
verdict: needs-fix
tags: [io-performance, long-method, dead-code, good-decomposition]
complexity:
  time: O(N² log N)
  space: O(N²)
generatedBy: claude-code-local
generatedAt: 2026-08-16
---

# 보급로 (swea/1249) — seongil

## 접근

`Node(r, c, cost)` + `Comparable` 로 우선순위 큐를 쓰고, `visited` 로 확정된 칸을 표시하는 다익스트라.
답은 맞다.

`inRange` 를 별도 메서드로 뽑은 건 좋다 — 4방향 탐색에서 조건식이 네 개씩 늘어지는 걸 막아준다.
`visited` 를 꺼낼 때 찍고(`visited[r][c] = true`) 이웃에서 확인하는 구조도 다익스트라의
"확정 집합" 개념을 그대로 옮긴 정석이다.

## 개선점

### 1. (중요) 테스트케이스마다 출력한다 — `io-performance`

```java
for (int i = 1; i <= t; i++) {
    ...
    StringBuilder sb = new StringBuilder();   // 매번 새로 만들고
    sb.append("#").append(i).append(" ").append(val).append("\n");
    System.out.print(sb);                      // 매번 출력
}
```

`System.out` 은 호출마다 flush 가 걸린다. 테스트케이스가 많은 SWEA 문제에서 이것만으로
시간 초과가 나는 경우가 실제로 있다. `StringBuilder` 를 루프 **밖에** 하나 두고 마지막에 한 번만 출력하면 된다.

```java
StringBuilder sb = new StringBuilder();          // 루프 밖
for (int i = 1; i <= t; i++) {
    ...
    sb.append("#").append(i).append(" ").append(val).append("\n");
}
System.out.print(sb);                            // 한 번만
```

같은 회차 김준수·안찬웅 코드가 이 형태다.

### 2. (중요) `package com.ssafy.swb;` 가 남아 있다 — `dead-code`

IDE 패키지 구조가 그대로 딸려왔다. 이 파일은 `com/ssafy/swb/` 디렉터리 안에 있어야만 실행되고,
저장소에서는 `이성일/week1/` 에 있어서 경로가 맞지 않는다. 제출본에도 들어갈 이유가 없다.

같은 이유로 파일 끝의 설계 메모(`// T 입력받고...`, `// 2차원배열 visited관리...`)도 정리 대상이다.
코드를 다 쓴 뒤 남은 계획 메모라 지금은 본문과 중복된다.

### 3. (중요) 죽은 큐 항목을 꺼낼 때 걸러내지 않는다

```java
Node v = pq.poll();
int r = v.r, c = v.c;
visited[r][c] = true;      // 이미 true 일 수 있다
```

같은 칸이 큐에 여러 번 들어가므로 확정된 칸을 또 꺼내게 된다. `!visited[ar][ac]` 가 있어서
**답은 맞지만**, 이미 처리한 칸의 4방향을 다시 도는 낭비가 남는다.

```java
Node v = pq.poll();
if (visited[v.r][v.c]) continue;   // 이 한 줄
visited[v.r][v.c] = true;
```

### 4. (사소) `dijkstra` 인자가 4개다 — `long-method`

```java
public static int dijkstra(PriorityQueue<Node> pq, int[][] dist, boolean[][] visited, int[][] board)
```

호출부에서 `pq` 를 만들어 시작 노드까지 넣어서 넘기고 있어서, 다익스트라의 시작점이 어디인지
메서드 안에서는 보이지 않는다. `board` 와 `n` 을 필드로 두고 `dijkstra(0, 0)` 형태로 바꾸면
"어디서 출발하는가"가 시그니처에 드러난다.

## 복잡도

- 시간: `O(N² log N)` — 적정하다.
- 공간: `O(N²)` — `dist` + `visited` + `board`. 적정하다.

## 요약

알고리즘은 정석이고 `inRange` 분리와 `visited` 운용은 오히려 모범에 가깝다.
다만 테스트케이스마다 출력하는 부분은 SWEA에서 실제로 시간 초과를 부를 수 있으니 먼저 고치는 게 좋다.
`package` 선언과 하단 계획 메모는 제출 전에 지우는 습관을 들이면 된다.
