---
platform: programmers
problemId: "67259"
author: seongil
source: 이성일/week2/월경주로건설.java
week: 2
compiles: false
verdict: wrong
tags: [missing-return, dead-code, logic-edge-case]
complexity:
  time: 측정 불가 (컴파일 실패)
  space: O(N^2)
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# 경주로 건설 (programmers/67259) — seongil

## 접근

`Road` 클래스에 `(행, 열, 방향, 비용)` 을 담고 `ArrayDeque` 로 BFS + 완화를 돌리는 구조다.
`costs` 를 방향 축을 포함해 3차원으로 잡은 것, 시작 칸에서 나가는 첫 이동만 따로 100원으로 처리한 것,
둘 다 이 문제에서 필요한 판단이다.

다만 **지금 이 파일은 컴파일되지 않는다.** 아래 1번을 먼저 고쳐야 나머지를 볼 수 있다.

## 개선점

### 1. (치명) 배열 차원이 선언과 사용에서 어긋난다 — `missing-return`

```java
costs = new int[4][size][size];      // [방향][행][열] 로 선언
...
costs[newR][newC] = newRoad.cost;    // [행][열] 로 두 개만 인덱싱
```

`costs[newR][newC]` 는 `int[]` 인데 `int` 를 대입하고 있다. 실제 컴파일 오류:

```
error: incompatible types: int cannot be converted to int[]
                    costs[newR][newC] = newRoad.cost;
                                               ^
error: bad operand types for binary operator '>'
                if (newRoad.cost > costs[newR][newC]) continue;
  first type:  int
  second type: int[]
```

선언이 `[방향][행][열]` 이므로 사용도 방향을 먼저 써야 한다.

```java
costs[i][newR][newC] = newRoad.cost;
...
if (newRoad.cost > costs[i][newR][newC]) continue;
```

`i` 는 지금 진행 방향이다 (`newRoad.dir` 과 같은 값이므로 `costs[newRoad.dir][newR][newC]` 로 써도 된다).

같은 문제 `안찬웅`·`이승주` 풀이는 `[행][열][방향]` 순으로 잡았다. 어느 순서든 상관없지만,
**선언과 사용이 같아야 한다.** 순서를 자주 헷갈린다면 다른 사람들처럼 방향을 마지막에 두는 쪽이
`Arrays.fill(dist[i][j], MAX)` 로 초기화하기 편해서 실수가 줄어든다.

### 2. (치명) 시작 칸 처리에서 방향별 비용이 뭉개진다 — `logic-edge-case`

```java
for (int i = 0; i < 4; i++) {
    costs[i][0][0] = start.cost;     // 네 방향 모두 0 — 여기까지는 맞다
}
...
if (road.r == 0 && road.c == 0) {
    newRoad.cost = 100;
    deq.add(newRoad);
    costs[newR][newC] = newRoad.cost;
    continue;                        // 갱신 검사 없이 무조건 넣는다
}
```

시작 칸에서 나가는 이동은 커브가 아니므로 100원이 맞다.
다만 이 분기는 **`costs` 갱신 검사(`newRoad.cost > costs[...]` )를 건너뛰고 무조건 큐에 넣는다.**
시작 칸은 한 번만 꺼내지므로 지금은 사고가 안 나지만, 완화 로직이 한 곳에 모여 있지 않아
1번을 고칠 때 여기도 같이 손봐야 한다.

`Road` 에 `dir = -1` 인 시작 노드를 만들고 "`dir == -1` 이면 커브 비용 없음" 으로 처리하면
이 분기 자체가 없어진다. `안찬웅` 풀이가 그 방식이다.

```java
int nCost = road.cost + 100 + (road.dir != -1 && road.dir != i ? 500 : 0);
```

### 3. (사소) 빈 반복문이 남아 있다 — `dead-code`

```java
for (int i = 0; i < 2; i++) {

}
```

본문이 비어 있다. 시작 방향 두 개를 큐에 넣으려다 만 흔적으로 보인다.
지우거나, 원래 의도대로 채워야 한다.

주석 처리된 `visited` 검사도 같이 남아 있다.

```java
// if (!inRange(size, newR, newC) || visited[i][newR][newC] || (board[newR][newC] == 1)) continue;
```

### 4. (사소) `inRange` 가 삼항 연산자로 boolean 을 만든다

```java
return ((x>=0 && x < size) && (y >= 0 && y < size)) ? true : false;
```

이미 `boolean` 인 식에 `? true : false` 를 붙였다. 그대로 반환하면 된다.

```java
return x >= 0 && x < size && y >= 0 && y < size;
```

## 복잡도

- 시간: 컴파일이 안 돼서 실측 불가. 구조상으로는 `O(N^2)` 수준의 BFS + 완화다
- 공간: `O(N^2)` — `costs` 가 `4 * N * N`

## 요약

방향을 상태에 넣는 접근 자체는 맞는데, `costs` 를 `[방향][행][열]` 로 선언해놓고
`[행][열]` 로 쓰는 바람에 컴파일이 안 된다. 인덱스 하나만 넣으면 통과한다.
그 뒤에 시작 칸 분기를 `dir = -1` 센티넬로 정리하면 완화 로직이 한 곳으로 모인다.
`이성일/week3/월경주로건설.java` 는 전체가 주석 처리돼 있어 이 week2 파일을 대상으로 봤다.
