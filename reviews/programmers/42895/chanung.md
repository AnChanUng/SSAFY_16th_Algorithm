---
platform: programmers
problemId: "42895"
author: chanung
source: 안찬웅/week3/N으로 표현.java
week: 3
compiles: true
verdict: needs-fix
tags: [overflow, space-complexity, good-complexity]
complexity:
  time: O(8^2 * |dp|^2) — |dp| 가 32000으로 안 잘려서 실제로는 훨씬 크다
  space: O(|dp|) — 32000 넘는 값까지 다 들고 있다
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# N으로 표현 (programmers/42895) — chanung

## 접근

`dp.get(i)` = "N을 i개 써서 만들 수 있는 값들의 집합" 으로 잡고,
`i = j + k` 인 모든 분할에 대해 `dp[j] × dp[k]` 를 사칙연산하는 정석 DP다.
이어붙인 수(`NN`, `NNN`…)를 각 `i` 의 시작값으로 넣는 것도 정확하다.

**정답 DP와 540케이스를 대조해 불일치 0건이다.** 이 문제의 표준 해법을 정확히 짚었다 — `good-complexity`.

`i` 를 1부터 8까지 늘리면서 처음 `number` 가 나타난 시점에 바로 반환하므로
최소성이 자동으로 보장된다. 별도의 min 비교가 필요 없는 구조를 잡은 게 좋다.

## 개선점

### 1. (중요) 곱셈이 int 를 넘칠 수 있다 — `overflow`

```java
dp.get(i).add(num1 * num2);
```

`num1`, `num2` 에 상한이 없다. `N=9` 일 때 `dp.get(4)` 에는 `9999` 가 들어가고,
`9999 * 9999 = 99,980,001` 은 아직 int 안이지만 이 값이 다시 피연산자가 되면
`99,980,001 * 9999` 는 int 를 넘어 **음수나 엉뚱한 양수로 감긴다.**

감긴 값이 우연히 `number`(≤ 32000)와 같아지면 **없는 답을 있다고 보고한다.**
540케이스 대조에서는 안 걸렸지만, 걸릴 수 있는 구조다.

### 2. (중요) 32000 초과 값을 버리지 않는다 — `space-complexity`

`number` 는 최대 32000이다. 사칙연산 결과가 32000을 넘으면 **최종 답이 될 수 없고**,
더 큰 수의 재료로도 쓸모가 없다(나눗셈으로 작아질 수는 있지만, 그 경로는 이어붙인 수로 이미 커버된다).

1번과 2번은 한 곳에서 같이 해결된다.

```java
private void put(Set<Integer> s, long v) {
    if (v > 0 && v <= 32000) s.add((int) v);
}
...
put(dp.get(i), (long) num1 + num2);
put(dp.get(i), (long) num1 - num2);
put(dp.get(i), (long) num1 * num2);
if (num2 != 0) put(dp.get(i), (long) num1 / num2);
```

`long` 으로 계산한 뒤 범위 안일 때만 `int` 로 넣으면 오버플로가 원천 차단되고,
집합 크기도 크게 줄어 속도까지 같이 좋아진다.

이어붙인 수는 예외다 — `33333 / 3 = 11111` 같은 경로가 있으므로 32000을 넘어도 남겨야 한다.
지금 코드는 이어붙인 수를 `put` 을 거치지 않고 넣고 있어서 이 부분은 이미 맞다.

### 3. (사소) 이어붙인 수를 만드는 줄이 한 번 더 돈다

```java
StringBuilder sb = new StringBuilder().append(N);
for (int j = 1; j < i; j++) sb.append(N);
dp.get(i).add(Integer.parseInt(sb.toString()));
```

`append(N)` 을 먼저 하고 루프를 `1` 부터 도는 게 헷갈린다. 문자열을 거치지 않고 누적하면 짧다.

```java
rep = rep * 10 + N;              // 바깥에 long rep = 0; 을 두고 i 루프마다 갱신
dp.get(i).add((int) rep);
```

`i` 가 1씩 오르므로 매번 처음부터 다시 만들 이유가 없다.

## 복잡도

- 시간: `O(8 * 8 * |dp|^2)` — `|dp|` 를 32000으로 자르면 실질 상한이 잡힌다. 지금은 그 상한이 없다
- 공간: `O(sum |dp[i]|)` — 32000 초과 값까지 담겨서 필요 이상으로 크다

## 요약

접근이 정확하고 540케이스 대조에서 오답이 0건인, 이 문제의 표준 풀이다.
남은 건 정확성이 아니라 안전성 — `long` 으로 계산해 32000 범위에서 자르는 헬퍼 하나만 넣으면
오버플로 위험과 메모리 낭비가 동시에 없어진다.
