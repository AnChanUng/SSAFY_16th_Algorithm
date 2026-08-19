---
platform: programmers
problemId: "42895"
author: junsoo
source: 김준수/week3/N으로 표현.java
week: 3
compiles: true
verdict: wrong
tags: [logic-edge-case, wrong-algorithm, duplicate-code, magic-number]
complexity:
  time: O(40^8) 최악 — 큐에 중복 상태를 그대로 다시 넣는다
  space: O(40^8) 큐 길이
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# N으로 표현 (programmers/42895) — junsoo

## 접근

`dp[i]` 를 "i를 만드는 데 필요한 최소 N 개수"로 잡고 BFS로 퍼뜨린다.
주석에 전략을 먼저 적고 들어간 게 좋다 — `N/N = 1`(2개), `NN/N = 11`(3개) 처럼
**1로 이루어진 수를 만드는 비용을 따로 계산해 `oneList` 로 분리한 발상**은 이 문제에서 흔치 않고,
실제로 많은 케이스를 커버한다.

다만 이 구조에는 아래 두 가지 결함이 있다.

## 개선점

### 1. (치명) `NNNNN` 이 실제로는 `NNNN` 이다 — `logic-edge-case`

```java
int NNNN  = Integer.parseInt(String.format("%d%d%d%d", N, N, N, N));
int NNNNN = Integer.parseInt(String.format("%d%d%d%d", N, N, N, N));  // %d 가 4개
```

포맷 문자열의 `%d` 가 4개뿐이라 `NNNNN` 에 `NNNN` 과 같은 값이 들어간다.
`NList` 는 5개짜리처럼 보이지만 실제로는 4자리까지만 만든다.

실제로 돌려본 결과:

```
solution(1, 11111)   기대 5   실제 7
```

`11111` 은 1을 5개 이어붙이면 끝이라 답이 5인데, 5자리 수가 아예 생성되지 않아
`oneList` 쪽 우회 경로(`+2` 비용)로 돌아가면서 7이 나온다.

```java
int NNNNN = Integer.parseInt(String.format("%d%d%d%d%d", N, N, N, N, N));
```

애초에 다섯 개를 따로 선언하는 대신 루프로 만들면 이런 오타가 날 자리가 없다. `magic-number` 도 같이 사라진다.

```java
int[] NList = new int[5];
long rep = 0;
for (int i = 0; i < 5; i++) { rep = rep * 10 + N; NList[i] = (int) rep; }
```

### 2. (치명) "이미 만든 수 op 이미 만든 수" 를 못 만든다 — `wrong-algorithm`

큐에서 꺼낸 `num` 에 **`NList`/`oneList` 의 단순한 수만** 연산한다.
즉 `복합수 op 단순수` 만 되고 `복합수 op 복합수` 가 안 된다.

실제로 돌려본 반례:

```
solution(1, 5)   기대 4   실제 5
```

정답 경로는 `11 / (1 + 1) = 5` 다. `11`(2개)과 `1+1`(2개) 둘 다 조합으로 만든 수라
이 코드의 탐색 공간 밖에 있다.

정답 DP와 540케이스를 대조한 결과 **72건 불일치**했다. 위 오타를 고쳐도 이 결함은 남는다.

고치려면 "N을 i개 써서 만들 수 있는 값들의 집합"을 개수별로 들고, 두 집합을 곱하는 형태로 바꿔야 한다.

```java
List<Set<Integer>> dp = new ArrayList<>();   // dp.get(i) = N을 i개 써서 만드는 값들
for (int i = 1; i <= 8; i++) {
    dp.get(i).add(repunit(N, i));            // 이어붙인 수
    for (int j = 1; j < i; j++)
        for (int a : dp.get(j)) for (int b : dp.get(i - j)) {
            // a+b, a-b, a*b, a/b 를 dp.get(i) 에 넣는다
        }
    if (dp.get(i).contains(number)) return i;
}
```

이러면 `oneList` 같은 별도 장치가 필요 없어진다. `1` 도 `N/N` 으로 `dp.get(2)` 에 자연히 들어간다.

### 3. (중요) 큐에 방문 검사 없이 넣는다 — `time-complexity`

꺼낼 때만 `count > 8` 로 거르고, **넣을 때는 아무 검사도 안 한다.**
한 노드가 40개를 낳으므로 최악에 `40^8` 규모다.
지금은 위 두 결함 때문에 탐색 공간이 우연히 작아 통과하지만, 구조 자체는 위험하다.

넣기 전에 `dp[next] <= nextCount` 면 버리는 가지치기가 필요하다.

### 4. (사소) 사칙연산 4줄이 두 번 복사돼 있다 — `duplicate-code`

`NList` 루프와 `oneList` 루프의 본문이 비용 상수(`+1` vs `+2`)만 빼고 같다.
`{값, 비용}` 쌍의 배열 하나로 합치면 8줄이 4줄이 된다.

## 복잡도

- 시간: `O(40^8)` — 중복 상태를 걸러내지 않아 큐가 지수로 늘어난다. 지배적인 건 큐 확장이다
- 공간: `O(40^8)` — `dp` 는 32001칸으로 작지만 큐가 그만큼 커진다

## 요약

전략을 주석으로 먼저 세우고 들어간 점, `1`을 만드는 비용을 따로 계산한 발상은 좋다.
다만 `NNNNN` 포맷 문자열 오타 하나와, `복합수 op 복합수` 를 만들 수 없는 탐색 구조가 겹쳐
540케이스 중 72건이 틀린다. 개수별 집합 DP로 바꾸면 두 문제가 동시에 사라진다.
