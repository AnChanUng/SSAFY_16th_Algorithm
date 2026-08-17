---
platform: programmers
problemId: "42627"
author: seungjoo
source: 이승주/week2/우선순위 컨트롤러.java
week: 2
compiles: true
verdict: good
tags: [good-complexity, good-readability, naming]
complexity:
  time: O(N log N)
  space: O(N)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 디스크 컨트롤러 (programmers/42627) — seungjoo

## 접근

요청 시각순으로 정렬해두고 포인터 하나로 훑으면서, 현재 시각까지 도착한 작업만 우선순위 큐에 넣는다.
큐에서는 소요시간이 짧은 것부터 꺼낸다. **33줄로 네 명 중 가장 짧고, 복잡도도 가장 좋다.**

```java
while (index < jobs.length && jobs[index][0] <= time) {
    pq.offer(jobs[index]);   // 도착한 것만 큐로
    index++;
}
```

각 작업이 큐에 딱 한 번 들어갔다 한 번 나온다. 되돌려 넣거나 다시 훑는 일이 없어서 `O(N log N)` 이다.

유휴 처리도 정확하다.

```java
} else {
    time = jobs[index][0];   // 큐가 비면 다음 작업 도착 시각으로 점프
}
```

1씩 올리지 않고 바로 건너뛴다. 이 문제 제약(요청 시각 ≤ 1000)에서는 차이가 작지만,
시각 범위가 큰 문제에서는 이 한 줄이 시간 초과를 가른다.

## 잘한 점

### 동점 처리를 굳이 안 넣은 게 맞다

우선순위 큐 비교자가 소요시간 하나뿐이다.

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> (a[1] - b[1]));
```

소요시간이 같은 두 작업의 순서를 바꿔도 **반환 시간 총합은 변하지 않는다.**
둘 다 이미 큐에 있으므로, 시작 시각 `t` 기준으로

- A 먼저: `(t+d-a₀) + (t+2d-b₀)`
- B 먼저: `(t+d-b₀) + (t+2d-a₀)`

두 합이 같다. 다른 두 명은 요청시각·작업번호까지 비교자에 넣었는데, 없어도 답이 같다.

## 개선점

### 1. (사소) 파일명이 문제 이름과 다르다 — `naming`

`우선순위 컨트롤러.java` 인데 문제는 **디스크 컨트롤러**다.
사이트는 `data/problems.json` 의 alias 로 흡수해서 정상 인식하지만,
저장소를 직접 훑는 사람에게는 다른 문제로 보인다. 다음부터는 문제 이름 그대로 두는 게 좋다.

### 2. (사소) 비교자를 뺄셈으로 썼다

```java
(a,b) -> (a[1] - b[1])
Arrays.sort(jobs, (a,b) -> a[0] - b[0]);
```

이 문제는 값이 1,000 이하라 **터지지 않는다.** 다만 `SWEA1249` 리뷰에서도 같은 지적이 있었으니
습관을 `Integer.compare(a[1], b[1])` 로 바꿔두면 값이 큰 문제에서 사고를 막을 수 있다.

### 3. (사소) 안쪽 `while` 의 닫는 괄호가 열 0에 있다

```java
            while (index < jobs.length && jobs[index][0] <= time) {
                pq.offer(jobs[index]);
                index++;
}
```

동작에는 영향이 없지만 블록 구조가 눈으로 안 잡힌다. IDE 자동 정렬(`Ctrl+Alt+L`)이면 끝난다.

## 복잡도

- 시간: `O(N log N)` — 정렬 + 각 작업이 큐를 한 번씩 통과. 네 명 중 유일하게 이 복잡도다.
- 공간: `O(N)` — 큐. 적정하다.

## 요약

교과서적인 형태다. 각 작업이 큐를 한 번만 통과하고, 유휴 시간은 점프로 건너뛴다.
지적할 게 파일명과 뺄셈 비교자뿐이고 둘 다 로직 밖의 문제다.
