---
platform: programmers
problemId: "67259"
author: junsoo
source: 김준수/week3/경주로 건설.java
week: 3
compiles: true
verdict: needs-fix
tags: [time-complexity, wrong-algorithm, magic-number]
complexity:
  time: 지수 (DFS 재탐색) — 실측 n=25에서 445ms
  space: O(N^2) + 재귀 깊이 O(N^2)
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# 경주로 건설 (programmers/67259) — junsoo

## 접근

주석의 시행착오 기록이 이 파일에서 제일 값지다.

> 2차원 dist 배열을 사용했으나 특정 지점에서는 최소비용이 아니나 결과적으로 최소비용이 되는 반례가 있었음

**이 문장이 이 문제의 핵심을 정확히 짚었다.** 같은 칸이어도 진입 방향이 다르면 이후 비용이 달라지므로
`dist` 가 2차원이면 안 된다. 실제로 `이승주` 의 실패 버전이 정확히 그 함정에 빠졌다.
거기서 3차원 `dist[x][y][dir]` 로 옮겨간 판단이 맞다.

커브 비용을 `500 + 100 = 600` 으로 한 번에 더하는 것도 정확하다.

**정답 다익스트라와 무작위 판 400개를 대조해 불일치 0건이다.** 답은 맞는다.
문제는 속도다.

## 개선점

### 1. (중요) DFS라서 같은 상태를 몇 번이고 다시 판다 — `time-complexity`

주석에 "백트래킹으로 변경했으나 시간초과 발생함 / 프루닝 조건을 여럿 추가해도 시간초과를 피할 수 없음"
이라고 적혀 있는데, **지금 코드도 여전히 DFS다.** 실제로 재봤다.

```
n=25 빈 판(벽 0개, 최악)
  junsoo    445ms
  chanung     1ms   (다익스트라)
  seungjoo    1ms   (BFS + 완화)
  정답 DP     1ms
```

400배 차이다. 통과는 하지만 여유가 없다.

원인은 가지치기 조건에 있다.

```java
else if(next.cost > dist[next.x][next.y][next.dir]){
    continue;
}
else{
    dist[next.x][next.y][next.dir] = next.cost;
    dfs(next);
}
```

`>` 이므로 **비용이 같을 때(`==`) 재귀로 다시 들어간다.** 격자에서 같은 비용으로 같은 상태에
도달하는 경로는 아주 많아서, 여기서 지수적으로 갈라진다.

`>=` 로 바꾸면 즉시 크게 줄어든다.

```java
else if(next.cost >= dist[next.x][next.y][next.dir]) continue;
```

다만 근본적으로는 **탐색 순서를 비용 오름차순으로 바꾸는 것**이 맞다.
다익스트라(우선순위 큐)나 BFS + 완화로 가면 각 상태를 사실상 한 번씩만 처리한다.
같은 문제 `안찬웅`·`이승주` 풀이가 각각 그 두 방식이고 둘 다 1ms다.

### 2. (중요) 목적지에 닿으면 `return` 이라 형제 방향을 버린다 — `wrong-algorithm`

```java
if(next.x == board.length - 1 && next.y == board.length - 1){
    dist[next.x][next.y][next.dir] = Math.min(...);
    return;          // continue 가 아니다
}
```

`return` 은 **현재 칸에서 아직 안 본 나머지 방향까지 통째로 포기**한다.
의도는 "도착했으니 더 갈 필요 없다"였을 텐데, 그 뜻이라면 `continue` 여야 한다.

400판 대조에서 이 때문에 틀린 경우는 안 나왔다.
도착 칸에 인접한 상태에서 멀리 돌아가는 경로가 더 싼 경우가 만들어지기 어렵기 때문으로 보인다.
**즉 지금은 우연히 안전하다.** 하지만 "왜 안전한지"를 설명할 수 있어야 남겨둘 수 있는 코드다.
`continue` 로 바꿔도 답은 같고 논리는 명확해진다.

### 3. (사소) `dist[0][0][k] = 0` 을 네 줄로 쓴다 — `magic-number`

```java
dist[0][0][0] = 0;
dist[0][0][1] = 0;
dist[0][0][2] = 0;
dist[0][0][3] = 0;
```

`Arrays.fill(dist[0][0], 0);` 한 줄이다.
초기화 3중 루프도 `for (int[][] p : dist) for (int[] q : p) Arrays.fill(q, Integer.MAX_VALUE);` 로 줄어든다.

`600` 과 `100` 도 상수로 빼면 "커브 500 + 직선 100" 이라는 주석이 코드에 들어온다.

### 4. (사소) `Car` 가 static 이 아닌 내부 클래스다 — `nonstatic-inner-class`

`Car` 는 바깥 인스턴스의 상태를 전혀 안 쓴다. `static class Car` 로 두면
숨은 바깥 참조가 사라져서 객체가 가벼워진다. 지금은 `dfs` 가 인스턴스 메서드라 컴파일되지만,
`solution` 을 `static` 으로 바꾸는 순간 깨지는 구조이기도 하다.

## 복잡도

- 시간: 이론상 지수. `==` 재탐색 때문에 상태당 방문이 1회로 묶이지 않는다. 실측 n=25에서 445ms
- 공간: `O(N^2)` 배열 + 재귀 깊이 `O(N^2)` — n=25면 깊이 600 이상까지 내려갈 수 있다

## 요약

2차원 `dist` 의 반례를 스스로 찾아 3차원으로 옮긴 판단이 정확하고, 답도 맞는다(400판 오답 0건).
남은 건 전부 속도다 — 가지치기를 `>=` 로 바꾸는 한 줄이 즉효고,
근본적으로는 다익스트라나 BFS 완화로 옮기면 445ms가 1ms가 된다.
