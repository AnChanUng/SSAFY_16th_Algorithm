---
platform: programmers
problemId: "43238"
author: seongil
source: 이성일/week2/입국심사.java
week: 2
compiles: true
verdict: needs-fix
tags: [overflow, uninitialized-state, naming, good-readability]
complexity:
  time: O(M log(max·n))
  space: O(1)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 입국심사 (programmers/43238) — seongil

## 접근

시간을 매개변수로 둔 파라메트릭 서치. 답은 맞다.

주석의 반례가 이 문제의 핵심을 정확히 짚었다.

```java
// 일단 정답 후보지만, t = 9, times = [2,4]  -> t = 8가 최적인 반례가 있다.
// 따라서, 정답으로 정해두고, 한번 더 탐색한다.
```

실제로 `times = [2,4]`, `n = 6` 일 때 `t = 9` 도 `t = 8` 도 6명을 처리한다.
"조건을 만족하는 값을 찾았다고 멈추면 안 되고, 더 작은 값이 있는지 계속 봐야 한다"는
파라메트릭 서치의 함정을 반례로 못박아둔 게 좋다.

시행착오 기록도 솔직하다. 다만 한 가지만 짚자면 — `3. 구현력이 떨어진다` 는 과한 자평이다.
이 코드는 상한·하한·갱신 조건이 모두 맞다. 틀린 건 아래 하나뿐이고, 그건 나머지 두 명도 똑같이 틀렸다.

## 개선점

### 1. (중요) 누적 합이 `long` 을 넘길 수 있다 — `overflow`

```java
long worstTime = longestTime * (long) n;    // 가장 느린 심사관 기준
binarySearch(1, worstTime, times);
...
for (int t: times){
    cnt += (midTime) / (long) t;             // 끝까지 다 더한다
}
```

제약은 `n ≤ 10⁹`, `times.length ≤ 10⁵`, `times[i] ≤ 10⁹` 다.

| | 값 |
|---|---|
| `worstTime = max·n` | `10¹⁸` |
| 첫 `midTime` | 약 `5 × 10¹⁷` |
| 소요시간 1분 심사관 10만 명이면 `cnt` | `5 × 10²²` |
| `long` 최대 | `9.2 × 10¹⁸` |

`cnt` 가 음수로 뒤집히면 `cnt >= people` 이 거짓이 되어 탐색이 반대로 간다.
예외 없이 답만 틀린다.

```java
for (int t : times) {
    cnt += midTime / t;
    if (cnt >= people) break;   // 필요한 만큼만 세고 멈춘다
}
```

같은 회차에서 이승주는 이 `break` 를 넣었고, 상한도 `times[0] * n` 으로 잡았다.
가장 빠른 심사관 혼자 n명을 처리하는 시간이라 상한으로 유효하면서 구간이 훨씬 좁다.

### 2. (중요) `answer` 와 `people` 이 `static` 이다 — `uninitialized-state`

```java
public static long people;
public static long answer;

public static void binarySearch(long minTime, long maxTime, int[] times){
    ...
    answer = midTime;      // 반환하지 않고 static 에 써둔다
}
```

`binarySearch` 가 `void` 라서 결과를 `static` 필드로 흘려보내고 있다.
채점기가 같은 JVM에서 여러 번 호출하면 **`answer` 의 이전 값이 남는다.**
지금은 항상 갱신되니 우연히 안전하지만, 조건이 한 번이라도 안 걸리면 앞 케이스의 답이 그대로 나간다.

값을 반환하게 바꾸면 이 위험이 통째로 사라진다.

```java
public static long binarySearch(long minTime, long maxTime, int[] times, long people) {
    long answer = maxTime;
    while (minTime <= maxTime) { ... }
    return answer;
}
```

### 3. (사소) `2l`, `1l` 은 대문자 `L` 로 — `naming`

```java
long midTime = (minTime+maxTime) / 2l;
maxTime = midTime - 1l;
```

소문자 `l` 은 숫자 `1` 과 거의 구분이 안 된다. `2L`, `1L` 로 쓰는 게 자바 관례다.
그리고 `minTime`/`maxTime` 이 이미 `long` 이라 `2` 로만 써도 결과는 같다.

### 4. (사소) `judges` 는 한 번만 쓰인다

```java
int judges = times.length;
Arrays.sort(times);
long longestTime = times[judges-1];
```

`times[times.length - 1]` 로 바로 쓰면 변수 하나가 준다.

## 복잡도

- 시간: `O(M log(max·n))` — M은 심사관 수. 상한을 `times[0] * n` 으로 좁히면 `log` 안이 줄어든다.
- 공간: `O(1)` — 적정하다.

## 요약

반례를 직접 만들어 "찾아도 멈추지 않는다"를 못박아둔 게 이 코드의 강점이다. 탐색 로직은 정확하다.
누적 합 오버플로와 `static` 결과 전달 두 가지만 고치면 되고, 둘 다 구조 문제가 아니라 습관 문제다.
