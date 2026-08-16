---
platform: swea
problemId: "1249"
author: seungjoo
source: 이승주/1주차/SWEA1249.java
week: 1
compiles: true
verdict: needs-fix
tags: [overflow, io-performance, good-complexity]
complexity:
  time: O(N² log N)
  space: O(N²)
generatedBy: claude-code-local
generatedAt: 2026-08-16
---

# 보급로 (swea/1249) — seungjoo

## 접근

`Node(x, y, cost)` + 우선순위 큐 다익스트라. 답은 맞다.

**네 명 중 유일하게 도착점을 꺼내는 순간 조기 종료한다.**

```java
if(current.x == N-1 && current.y == N-1) {
    return dist[N-1][N-1];   // 목적지에 도착했을때 최단 경로임
}
```

주석에 근거까지 적어놨는데 정확하다. 우선순위 큐에서 꺼낸 시점의 비용은 그 칸의 확정 최단값이므로,
도착점이 나오면 나머지 칸을 볼 이유가 없다. 격자 전체를 끝까지 훑는 다른 세 명보다 평균적으로 빠르다.

파일 상단의 시행착오 기록도 좋다.

> 처음에는 dfs 백트래킹으로 푸는 문제인줄 알고 시도하였지만 시간 초과, 이후 각 위치에 비용이 존재한다는 것을 깨닫고 다익스트라 진행

"칸마다 비용이 있으면 최단경로 문제"라는 인식이 이 문제의 핵심이다.

## 개선점

### 1. (중요) `compareTo` 를 뺄셈으로 구현했다 — `overflow`

```java
public int compareTo(Node o) {
    return this.cost - o.cost;
}
```

이 문제에서는 최대 비용이 `100 × 100 × 9 = 90,000` 이라 **터지지 않는다.** 답도 맞다.
문제는 습관이다. 두 값이 `Integer.MAX_VALUE` 와 음수처럼 벌어지면 뺄셈이 오버플로해서
부호가 뒤집히고, 정렬 순서가 조용히 깨진다. 우선순위 큐가 잘못된 순서로 돌아도 예외가 안 나서
찾기 아주 어려운 버그가 된다.

```java
public int compareTo(Node o) {
    return Integer.compare(this.cost, o.cost);
}
```

같은 회차에서 김준수·이성일은 `Integer.compare` 를 썼다. 비용을 누적하는 문제(다익스트라, 배낭 등)에서는
이쪽으로 통일하는 게 안전하다.

### 2. (중요) 테스트케이스마다 출력한다 — `io-performance`

```java
StringBuilder sb = new StringBuilder();   // 루프 안에서 매번 생성
sb.append("#").append(tc).append(" ").append(minTime);
System.out.println(sb);                    // 매번 출력
```

`System.out` 은 호출마다 flush 된다. 테스트케이스가 많으면 이것만으로 시간 초과가 날 수 있다.
`StringBuilder` 를 루프 밖에 하나 두고 `\n` 을 붙여 쌓은 뒤 마지막에 한 번만 출력하면 된다.

### 3. (사소) `max_value` 지역 변수는 없어도 된다

```java
int max_value = Integer.MAX_VALUE;
for(int i = 0; i < N; i++) Arrays.fill(dist[i], max_value);
```

`Arrays.fill(dist[i], Integer.MAX_VALUE)` 로 바로 쓰면 된다. 자바 관례상 지역 변수는
`snake_case` 가 아니라 `camelCase` 이기도 하다.

### 4. (사소) 조기 종료가 있어서 마지막 `return` 은 도달 조건이 특이하다

```java
return dist[N-1][N-1];   // 큐가 빌 때까지 도착점을 못 꺼낸 경우
```

이 문제는 모든 칸이 통행 가능해서 항상 도달하므로 실제로는 위쪽 `return` 에서 끝난다.
남겨두는 게 안전하지만, 왜 있는지 한 줄 주석이 있으면 읽는 사람이 덜 헷갈린다.

## 복잡도

- 시간: `O(N² log N)` — 조기 종료 덕에 평균은 이보다 빠르다.
- 공간: `O(N²)` — `dist` + `board`. 적정하다.

## 요약

조기 종료를 넣은 건 네 명 중 유일하고, 그 근거를 정확히 이해하고 있다.
`compareTo` 뺄셈과 테스트케이스별 출력 두 가지만 습관으로 고치면 된다.
둘 다 이 문제에서는 통과하지만, 더 큰 입력에서 조용히 터지는 종류다.
