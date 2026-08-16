---
platform: swea
problemId: "1249"
author: junsoo
source: 김준수/week1/SWEA1249.java
week: 1
compiles: true
verdict: good
tags: [redundant-collection, space-complexity, good-readability]
complexity:
  time: O(N² log N)
  space: O(N²)
generatedBy: claude-code-local
generatedAt: 2026-08-16
---

# 보급로 (swea/1249) — junsoo

## 접근

`Cell(time, x, y)` 클래스를 만들고 `Comparable` 을 구현해 우선순위 큐에 넣는 다익스트라.
답은 맞고, 파일 맨 위의 전략·시행착오 주석이 특히 좋다.

> 처음에는 visited 배열을 썼었으나, dist가 visited 배열의 역할을 대처 가능했음

이건 다익스트라를 제대로 이해했다는 신호다. `dist` 가 `INF` 인지 여부가 곧 미방문 표시이고,
완화 조건이 재방문을 걸러내므로 `visited` 는 없어도 된다. 리뷰 시간에 공유할 만한 내용이다.

## 개선점

### 1. (중요) `Cell[][] board` 는 `int[][]` 면 충분하다 — `redundant-collection`

```java
board = new Cell[N][N];
...
board[i][j] = new Cell(line.charAt(j) - '0', i, j);
```

칸마다 객체를 하나씩 만든다. N=100이면 **1만 개의 객체**가 생기는데, 실제로 쓰는 건 `.time` 뿐이다.
`Cell.x` / `Cell.y` 에는 자기 좌표가 들어가지만 알고리즘은 그 값을 읽지 않는다 — 이웃 좌표는
`nx`/`ny` 로 따로 계산한다.

```java
static int[][] board;
...
board[i][j] = line.charAt(j) - '0';
```

`Cell` 은 우선순위 큐에 넣을 때만 필요하다. 격자 저장까지 객체로 할 이유는 없다.
테스트케이스가 반복되면 GC 부담도 그만큼 늘어난다.

### 2. (중요) 죽은 큐 항목을 건너뛰지 않는다

```java
Cell cell = pq.poll();
int currTime = cell.time;
// 바로 4방향 탐색
```

큐에는 같은 칸이 여러 번 들어간다. 나중에 더 짧은 경로를 찾으면 새 항목을 넣지만 옛 항목은
남아 있기 때문이다. 완화 조건(`nextTime < dist[nx][ny]`) 덕에 **답은 맞지만**, 이미 확정된 칸을
꺼내서 4방향을 또 도는 낭비가 있다.

```java
Cell cell = pq.poll();
if (cell.time > dist[cell.x][cell.y]) continue;   // 이 한 줄
```

같은 회차 안찬웅 코드에 이 처리가 들어가 있으니 비교해보면 좋다.

### 3. (사소) `dist` 초기화가 입력 루프에 섞여 있다

```java
board[i][j] = new Cell(line.charAt(j) - '0', i, j);
dist[i][j] = Integer.MAX_VALUE;
```

한 루프에서 두 가지 일을 한다. `Arrays.fill` 로 분리하면 각 줄이 뭘 하는지 바로 보인다.

```java
for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
```

## 복잡도

- 시간: `O(N² log N)` — 적정하다.
- 공간: `O(N²)` — 다만 상수가 크다. `Cell[][]` 을 `int[][]` 로 바꾸면 칸당 객체 헤더(16바이트 이상)가
  사라지고 int 4바이트만 남는다.

## 요약

알고리즘 선택과 이해도는 좋고 주석이 팀에서 제일 친절하다.
격자를 객체 배열로 들고 있는 것과 죽은 큐 항목 처리 두 가지만 손보면 된다.
둘 다 답이 아니라 낭비의 문제다.
