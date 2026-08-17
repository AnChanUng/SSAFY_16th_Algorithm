---
platform: programmers
problemId: "43238"
author: chanung
source: 안찬웅/week2/입국심사.java
week: 2
compiles: true
verdict: needs-fix
tags: [overflow, good-readability, good-complexity]
complexity:
  time: O(M log(max·n))
  space: O(1)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 입국심사 (programmers/43238) — chanung

## 접근

시간을 매개변수로 둔 파라메트릭 서치. **31줄로 네 명 중 가장 짧다.**
군더더기 없이 이분탐색 한 덩어리로 끝냈고, 읽는 데 걸림이 없다.

```java
if(sum >= n) {
    high = mid - 1;
    time = mid;      // 정답 후보 갱신
} else {
    low = mid + 1;
}
```

"가능하면 답으로 찍어두고 더 줄여본다"는 파라메트릭 서치의 표준형이다.
`(long)` 캐스팅으로 상한 계산의 오버플로를 막은 것도 정확하다.

## 개선점

### 1. (중요) 누적 합이 `long` 을 넘길 수 있다 — `overflow`

```java
long high = (long) times[times.length-1] * n;   // 가장 느린 심사관 기준
...
for(int x : times) {
    sum += mid / x;                              // 끝까지 다 더한다
}
```

문제 제약은 `n ≤ 10⁹`, `times.length ≤ 10⁵`, `times[i] ≤ 10⁹` 다.
이 조합이 동시에 들어오면 터진다.

| | 값 |
|---|---|
| `high = max·n` | `10⁹ × 10⁹ = 10¹⁸` |
| 첫 `mid` | 약 `5 × 10¹⁷` |
| 소요시간 1분 심사관이 10만 명이면 `sum` | `10⁵ × 5×10¹⁷ = 5 × 10²²` |
| `long` 최대 | `9.2 × 10¹⁸` |

`sum` 이 음수로 뒤집히면 `sum >= n` 이 거짓이 되어 탐색이 반대로 간다.
예외가 안 나고 답만 조용히 틀리는 종류다.

**지금 채점을 통과한 건 테스트 데이터가 이 조합까지 안 갔기 때문**이지 코드가 안전해서가 아니다.

고치는 방법은 두 가지고, 둘 다 한 줄이다.

```java
// (a) 필요한 만큼만 세고 멈춘다
for(int x : times) {
    sum += mid / x;
    if (sum >= n) break;
}

// (b) 상한을 가장 '빠른' 심사관 기준으로 좁힌다
long high = (long) times[0] * n;   // 정렬 후 times[0] 이 최소
```

(b) 가 유효한 이유는 시간 `min·n` 이면 가장 빠른 심사관 혼자 `n` 명을 처리하기 때문이다.
상한으로 성립하면서 구간이 `max/min` 배 좁아지고, 각 항이 `n` 이하로 눌려 오버플로도 사라진다.

같은 회차 이승주가 (a)와 (b)를 둘 다 썼다. 비교해보면 좋다.

### 2. (사소) `time` 대신 `low` 를 반환하면 변수 하나가 준다

```java
long time = 1;          // 초기값 1은 근거가 없다
...
if(sum >= n) { high = mid - 1; time = mid; }
...
return time;
```

이분탐색이 끝나면 `low` 가 곧 "조건을 만족하는 가장 작은 값"이다.
`time` 을 따로 들고 다니지 않아도 `return low;` 로 끝난다.

지금은 `high` 가 항상 유효한 시간이라 `time` 이 반드시 갱신되지만,
초기값 `1` 은 "갱신이 안 되면 1을 반환한다"는 뜻이 되어 근거 없는 값이 답으로 나갈 여지를 남긴다.

### 3. (사소) `sum` 은 루프 안에서 선언하면 된다

```java
long sum;              // 밖에서 선언
while(low <= high) {
    sum = 0;           // 매번 리셋
```

`while` 안에서 `long sum = 0;` 으로 두면 리셋을 잊을 일 자체가 없어진다.

## 복잡도

- 시간: `O(M log(max·n))` — M은 심사관 수. 상한을 `min·n` 으로 바꾸면 `log` 안이 좁아진다.
- 공간: `O(1)` — 적정하다.

## 요약

가장 짧고 구조가 깔끔하다. 파라메트릭 서치 형태를 정확히 알고 있다.
누적 합 오버플로 하나만 막으면 되고, `break` 한 줄이나 상한 조정 한 줄 중 아무거나면 된다.
