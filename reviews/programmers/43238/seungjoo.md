---
platform: programmers
problemId: "43238"
author: seungjoo
source: 이승주/week2/입국심사.java
week: 2
compiles: true
verdict: good
tags: [good-complexity, good-readability]
complexity:
  time: O(M log(min·n))
  space: O(1)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 입국심사 (programmers/43238) — seungjoo

## 접근

시간을 매개변수로 두는 파라메트릭 서치. **네 명 중 유일하게 탐색 상한을 `times[0] * n` 으로 잡았다.**

```java
Arrays.sort(times);
long left  = times[0];
long right = (long) times[0] * n;   // 가장 빠른 심사관 * n
```

나머지 셋은 `times[max] * n` (가장 느린 심사관 기준)을 썼는데, 이쪽이 더 타이트하면서 **여전히 정답을 놓치지 않는다.**

- 시간 `times[0] * n` 이면 가장 빠른 심사관 혼자서 `n` 명을 처리한다 (`min·n / min = n`).
  즉 이 시간은 항상 "가능"하므로 상한으로 유효하다.
- 하한 `times[0]` 도 정확하다. 최소 한 명을 심사하려면 최소 그만큼은 걸린다.

이 선택 덕분에 얻는 게 하나 더 있는데, 아래에서 설명한다.

## 잘한 점

### 1. 탐색 상한이 오버플로까지 막아준다

`count += mid / time` 은 심사관 수만큼 누적된다. 문제 제약은
`n ≤ 10⁹`, `times.length ≤ 10⁵`, `times[i] ≤ 10⁹` 다.

상한을 `max·n` 으로 잡으면 `mid` 가 최대 `10¹⁸` 까지 간다. 여기에 소요시간 1분짜리 심사관이
여럿 섞여 있으면 한 항이 그대로 `10¹⁸` 이 되고, 10만 명분을 더하면 `long` (약 9.2×10¹⁸) 을 넘긴다.

이 코드는 `mid ≤ min·n` 이므로 각 항이
`mid / times[i] ≤ (min·n) / min = n ≤ 10⁹` 로 눌린다. 전부 더해도 `10⁵ × 10⁹ = 10¹⁴` 이라 안전하다.

### 2. 조기 `break` 가 한 겹 더 막아준다

```java
for (int time : times) {
    count += mid / time;
    if (count >= n) break;      // 필요한 만큼 넘으면 그만
}
```

판정에 필요한 건 "n 이상인가" 뿐이라 정확한 총합은 필요 없다.
불필요한 순회를 줄이면서, 누적값이 커질 여지도 같이 없앤다.

같은 회차 나머지 세 명은 이 `break` 가 없다. 비교해보면 좋다.

## 개선점

### 1. (사소) `right` 의 근거를 한 줄 적어두면 좋겠다

`times[0] * n` 이 상한으로 유효한 이유(가장 빠른 심사관 혼자 n명 처리)는
코드만 봐서는 바로 안 보인다. 오히려 "가장 느린 심사관을 써야 하는 것 아닌가?" 하고
잘못된 지적을 받기 쉬운 부분이다. 주석 한 줄이면 리뷰 시간이 절약된다.

### 2. (사소) 빈 줄이 많다

`while` 안팎으로 빈 줄이 2~3줄씩 들어가 있어 36줄 중 실질 코드는 20줄 남짓이다.
로직이 짧고 명확한 만큼 붙여두면 한눈에 들어온다.

## 복잡도

- 시간: `O(M log(min·n))` — M은 심사관 수. 탐색 구간이 다른 셋보다 `max/min` 배 좁다.
- 공간: `O(1)` — 정렬 외 추가 메모리 없음.

## 요약

네 명 중 가장 정확한 코드다. 탐색 상한을 `min·n` 으로 잡은 것이 핵심이고,
그게 성능뿐 아니라 오버플로 안전성까지 가져왔다.
근거를 주석으로 남겨두면 다음에 볼 사람이 헷갈리지 않는다.
