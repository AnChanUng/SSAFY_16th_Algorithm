---
platform: programmers
problemId: "42627"
author: chanung
source: 안찬웅/week2/디스크 컨트롤러.java
week: 2
compiles: true
verdict: good
tags: [redundant-loop, good-readability, good-complexity]
complexity:
  time: O(N log N + T)
  space: O(N)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 디스크 컨트롤러 (programmers/42627) — chanung

## 접근

요청 시각순 정렬 + 포인터로 도착한 작업만 큐에 넣고, 소요시간이 짧은 것부터 처리한다.
이승주와 같은 정석 구조이고 답도 맞다.

`Node` 를 만들어 `compareTo` 에 정렬 기준 세 개를 순서대로 넣은 게 읽기 좋다.

```java
public int compareTo(Node o) {
    if(this.spendTime != o.spendTime) return Integer.compare(this.spendTime, o.spendTime);
    if(this.requestTime != o.requestTime) return Integer.compare(this.requestTime, o.requestTime);
    return Integer.compare(this.number, o.number);
}
```

`Integer.compare` 를 쓴 것도 정확하다. 같은 회차 `베스트앨범` 에서도 이 형태를 썼는데,
그때 지적받은 건 비교자가 아니라 그 뒤의 순회 로직이었다. 비교자 작성은 안정적으로 잘한다.

`static class Node` 로 선언한 것도 맞다 — 김준수는 여기서 `static` 을 빠뜨렸다.

## 개선점

### 1. (사소) 유휴 시간을 1씩 올린다 — `redundant-loop`

```java
} else {
    time++;
    //time = jobs[idx][0];      // 주석으로만 남아 있다
}
```

큐가 비었다는 건 "다음 작업이 아직 도착 안 했다"는 뜻이므로,
`jobs[idx][0]` 로 **바로 점프**하면 된다. 주석에 이미 정답이 적혀 있다.

이 문제는 요청 시각이 1,000 이하라 최악에도 1,000번 헛도는 정도이고 통과한다.
다만 시각 범위가 큰 문제(예: 10⁹)에서는 이 한 줄이 시간 초과를 가른다.
상단 주석에서 스스로 짚은 부분이기도 하다.

> 3. 큐가 비어있을 때 -> (여기서 조건처리 생각은 못함)

주석 처리된 줄을 살리면 된다. `idx < jobs.length` 는 이 분기에 들어온 시점에서 이미 보장된다
(`done < jobs.length` 이고 큐가 비어 있으므로 아직 안 넣은 작업이 남아 있다).

### 2. (사소) 동점 처리는 없어도 답이 같다

`compareTo` 의 2·3순위(요청시각, 작업번호)는 정확하지만 **이 문제에서는 결과에 영향이 없다.**
소요시간이 같은 두 작업은 이미 둘 다 큐에 있으므로 순서를 바꿔도 반환 시간 총합이 같다.

- A 먼저: `(t+d-a₀) + (t+2d-b₀)`
- B 먼저: `(t+d-b₀) + (t+2d-a₀)`

이승주는 소요시간 하나만 비교했고 같은 답이 나온다.
넣어도 틀리지 않으니 그대로 둬도 되지만, "왜 필요한가"를 한 번 따져보면 좋다.

### 3. (사소) `Node.number` 는 동점 처리용으로만 쓰인다

2번대로 동점 처리가 불필요하다면 `number` 필드도 같이 사라진다.
그러면 `Node` 는 `(requestTime, spendTime)` 두 개짜리가 되어 `int[]` 로도 충분해진다.

## 복잡도

- 시간: `O(N log N + T)` — T는 유휴 구간 길이(최대 1,000). 1번을 고치면 `O(N log N)`.
- 공간: `O(N)` — 큐. 적정하다.

## 요약

구조와 비교자 모두 정석이다. `static class` 도 정확히 붙였다.
고칠 건 주석으로 이미 적어둔 한 줄을 살리는 것뿐이다.
