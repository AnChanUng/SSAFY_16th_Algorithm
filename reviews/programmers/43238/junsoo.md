---
platform: programmers
problemId: "43238"
author: junsoo
source: 김준수/week2/입국심사.java
week: 2
compiles: true
verdict: needs-fix
tags: [overflow, uninitialized-state, good-readability]
complexity:
  time: O(M log(max·n))
  space: O(1)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 입국심사 (programmers/43238) — junsoo

## 접근

시간을 매개변수로 둔 파라메트릭 서치. 판정을 `isValid(time)` 로 분리해서
"이 시간이면 되는가"와 "구간을 어떻게 좁히는가"가 코드에서 따로 읽힌다. 좋은 분리다.

상단 주석의 시행착오 기록도 정확하다.

> end의 최적 값은 가장 오래 걸리는 심사관이 모든 사람을 담당하는 것인데,
> times 배열이 int인데 long인 end에 저장하려고 하니 오버플로우가 발생했음

`(long) times[...] * n` 캐스팅을 스스로 찾아낸 부분이다. **다만 오버플로는 여기만이 아니다.**

## 개선점

### 1. (중요) 캐스팅으로 막은 건 상한 계산뿐, 누적 합은 그대로다 — `overflow`

```java
long end = (long) times[times.length - 1] * n;   // 여기는 막았다
...
private boolean isValid(long time){
    long result = 0;
    for(int t : times){
        result += time / t;                       // 여기는 안 막혔다
    }
```

제약은 `n ≤ 10⁹`, `times.length ≤ 10⁵`, `times[i] ≤ 10⁹` 다.

| | 값 |
|---|---|
| `end = max·n` | `10¹⁸` |
| 첫 `mid` | 약 `5 × 10¹⁷` |
| 소요시간 1분 심사관 10만 명이면 `result` | `5 × 10²²` |
| `long` 최대 | `9.2 × 10¹⁸` |

`result` 가 음수로 뒤집히면 `result >= n` 이 거짓이 되어 탐색이 반대 방향으로 간다.
예외 없이 답만 틀리므로 디버깅이 어렵다.

주석에서 짚은 것과 **같은 성격의 문제이고 같은 배열에서 나온다.**
"곱셈만 조심하면 된다"가 아니라 "누적도 조심해야 한다"까지 확장하면 된다.

```java
for(int t : times){
    result += time / t;
    if (result >= n) return true;   // 필요한 만큼만 세고 멈춘다
}
return false;
```

이러면 `result` 가 `n` 을 크게 넘길 일이 없다.

### 2. (중요) `n` 과 `times` 를 `static` 으로 들고 있다 — `uninitialized-state`

```java
static int n;
static int[] times;

public long solution(int n, int[] times) {
    this.n = n;              // static 필드를 this 로 대입
    this.times = times;
```

`static` 필드를 `this.` 로 대입하고 있다. 컴파일은 되지만 "인스턴스마다 따로"라는 오해를 부른다.
실제로는 클래스 전체가 공유하므로, 채점기가 같은 JVM에서 여러 번 호출하면
초기화를 한 번만 빠뜨려도 **이전 테스트케이스 값이 남는다.**

`isValid` 가 쓰는 건 `n` 과 `times` 뿐이니 인자로 넘기면 필드 자체가 필요 없다.

```java
private boolean isValid(long time, int n, int[] times) { ... }
```

### 3. (사소) `if/else` 로 `boolean` 을 만들 필요는 없다

```java
if(result >= n){
    return true;
}
else{
    return false;
}
```

`return result >= n;` 한 줄이면 같다.

### 4. (사소) `isValid` 안에 탭과 스페이스가 섞여 있다

```java
        long result = 0;
				
				// 각 심사관이 time 내에 담당할 수 있는 사람의 수를 더함        
```

주석 줄이 탭으로 들여쓰기돼 있어 에디터 설정에 따라 위치가 흔들린다.
IDE의 "탭을 스페이스로" 설정을 켜두면 자동으로 정리된다.

## 복잡도

- 시간: `O(M log(max·n))` — M은 심사관 수. 상한을 `times[0] * n` 으로 좁히면 `log` 안이 줄어든다
  (가장 빠른 심사관 혼자 n명을 처리하는 시간이므로 상한으로 유효하다).
- 공간: `O(1)` — 적정하다.

## 요약

`isValid` 분리와 상한 캐스팅 모두 정확하고, 시행착오를 기록해둔 것도 좋다.
정작 같은 종류의 오버플로가 누적 합에 남아 있는 게 아쉽다 — `break` 한 줄이면 막힌다.
