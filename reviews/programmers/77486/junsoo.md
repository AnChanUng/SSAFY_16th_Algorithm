---
platform: programmers
problemId: "77486"
author: junsoo
source: 김준수/week2/다단계 칫솔 판매.java
week: 2
compiles: true
verdict: needs-fix
tags: [time-complexity, redundant-loop, nonstatic-inner-class, good-readability]
complexity:
  time: O(S · N + N²)
  space: O(N)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 다단계 칫솔 판매 (programmers/77486) — junsoo

## 접근

`Seller` 객체 리스트를 만들고 0번에 `center` 를 넣어 트리 루트로 삼는다.
매출이 발생할 때마다 그 자리에서 추천인 체인을 따라 올라가며 정산한다.

**분배 계산은 정확하다.** 특히 상단 주석의 이 통찰이 이 문제의 핵심이다.

> 매출을 전체 정산한 후 배분금을 정산하면 절삭 오류 발생
> ex) 한 사람에게 500 500이 들어오면 정산금이 0.5, 0.5가 되어 절삭되어야 하는데
> 이전 코드로 하면 총 매출이 1000이므로 정산금이 1이 되어 절삭되지 않음

10% 절삭은 **매출 건별로** 일어난다. 합쳐놓고 나중에 나누면 절삭 횟수가 달라진다.
이걸 반례까지 만들어 정리해둔 건 리뷰 시간에 공유할 만하다.

`center` 를 0번 노드로 두고 `parent == -1` 로 루트를 표시한 것도 깔끔하다.
덕분에 "추천인이 없는 경우"를 따로 분기하지 않고 루프 조건 하나로 처리된다.

## 개선점

### 1. (치명) 이름을 찾을 때마다 전체를 훑는다 — 시간 초과 — `time-complexity`

두 곳에서 선형 탐색을 한다.

```java
// 추천인 초기화 — N × N
for(int i = 0; i < referral.length; i++){
    for(int j = 0; j < sellers.size(); j++){
        if(sellers.get(j).name.equals(referral[i])){ ... }
    }
}

// 매출 정산 — S × N
for(int i = 0; i < seller.length; i++){
    for(Seller s : sellers){
        if(s.name.equals(seller[i])){ ... }
    }
}
```

문제 제약은 `enroll` 최대 **10,000**, `seller` 최대 **100,000** 이다.

| 위치 | 연산 수 |
|---|---|
| 추천인 초기화 | 10,000 × 10,001 ≈ **1억** |
| 매출 정산 | 100,000 × 10,001 ≈ **10억** |

두 번째가 특히 치명적이다. 문자열 `equals` 10억 번은 자바에서 확실히 시간 초과다.
정답 로직인데 채점에서 떨어진다.

이름 → 인덱스 맵을 한 번만 만들면 둘 다 O(1) 조회가 된다.

```java
Map<String, Integer> idx = new HashMap<>();
for (int i = 0; i < enroll.length; i++) idx.put(enroll[i], i + 1);  // 0번은 center
idx.put("-", 0);

// 추천인 초기화 — N
for (int i = 0; i < referral.length; i++) sellers.get(i + 1).parent = idx.get(referral[i]);

// 매출 정산 — S
for (int i = 0; i < seller.length; i++) {
    Seller s = sellers.get(idx.get(seller[i]));
    ...
}
```

`"-"` 를 0번(center)으로 매핑해두면 기존 `parent == -1` 루트 표시와도 그대로 맞물린다.

같은 문제를 푼 나머지 세 명은 전부 `HashMap<String, Integer>` 를 썼다. 비교해보면 좋다.

### 2. (중요) 찾은 뒤에도 루프를 계속 돈다 — `redundant-loop`

```java
for(Seller s : sellers){
    if(s.name.equals(seller[i])){
        ... // 정산
    }
}   // 찾았는데도 끝까지 돈다
```

이름은 유일하므로 정산 후 `break` 해야 한다. 1번을 적용하면 이 루프 자체가 사라지지만,
선형 탐색을 유지하더라도 평균 절반의 비용은 줄어든다.

### 3. (사소) `Seller` 는 `static` 중첩 클래스여야 한다 — `nonstatic-inner-class`

```java
class Solution {
    class Seller{ ... }   // 내부 클래스
```

지금은 `Seller` 인스턴스마다 바깥 `Solution` 참조를 숨겨서 들고 다닌다.
`Seller` 는 `Solution` 의 상태를 전혀 쓰지 않으므로 `static class Seller` 가 맞다.
판매원이 1만 명이면 그만큼의 불필요한 참조가 생긴다.

### 4. (사소) `money` 와 `plus` 를 나눠 들 이유가 없다

```java
s.money += money - give;    // 본인 판매 몫
parent.plus += give;        // 추천으로 받은 몫
...
answer[i - 1] = sellers.get(i).money + sellers.get(i).plus;   // 결국 합친다
```

마지막에 더할 거라면 필드 하나면 된다. 둘로 나눠두면 "어느 쪽에 더해야 하지?" 를
매번 판단해야 해서 실수 여지만 생긴다.

## 복잡도

- 시간: `O(S · N + N²)` — 1번을 고치면 `O((S + N) · D)`. D는 추천 체인 깊이로,
  10% 절삭 때문에 금액이 빠르게 0이 되어 실질적으로 매우 얕다.
- 공간: `O(N)` — 판매원 수만큼. 적정하다.

## 요약

절삭 오류를 스스로 찾아내고 반례까지 정리한 게 이 코드의 가장 큰 강점이다. 분배 로직은 맞다.
문제는 이름 조회를 매번 선형 탐색으로 한다는 것 하나뿐이고, `HashMap` 한 줄이면 10억 연산이 10만으로 줄어든다.
