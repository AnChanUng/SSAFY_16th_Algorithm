---
platform: swea
problemId: "1249"
author: chanung
source: 안찬웅/week1/SWEA1249.java
week: 1
compiles: true
verdict: good
tags: [good-complexity, good-readability, dead-code]
complexity:
  time: O(N² log N)
  space: O(N²)
generatedBy: claude-code-local
generatedAt: 2026-08-16
---

# 보급로 (swea/1249) — chanung

## 접근

격자를 그래프로 보고 다익스트라. 우선순위 큐에 `int[]{x, y, cost}` 를 넣고 `dist` 로 최단 복구시간을 관리한다.

**4명 중 유일하게 `if(cost > dist[cx][cy]) continue;` 가 있다.** 이 한 줄이 교과서적으로 맞는 처리다.

```java
int[] cur = pq.poll();
...
if(cost > dist[cx][cy]) continue;   // 이미 더 좋은 값으로 확정된 칸
```

우선순위 큐에는 같은 칸이 여러 번 들어간다. 나중에 더 짧은 경로를 찾으면 새 항목을 넣지만
큐에 있던 옛 항목은 지워지지 않기 때문이다. 이 줄이 없어도 아래 완화 조건(`nCost < dist[nx][ny]`)
덕분에 답은 맞지만, 죽은 항목의 4방향 탐색을 매번 다시 돈다. 이걸 짚고 넘어간 건 잘한 판단이다.

클래스를 만들지 않고 `int[]` + 람다 비교자로 끝낸 것도 이 문제 규모에서는 적절하다.
객체 생성 비용이 없고 코드도 짧다.

## 개선점

### 1. (사소) `import java.lang.*;` 는 필요 없다 — `dead-code`

`java.lang` 은 컴파일러가 항상 자동으로 넣는다. 지워도 동작이 같다.

### 2. (사소) 입력 파싱이 행 길이에 의존한다

```java
String str = br.readLine();
for(int j=0; j<str.length(); j++) {
```

`n` 대신 `str.length()` 를 쓰고 있다. 지금은 같지만, 줄 끝에 공백이나 `\r` 이 섞여 들어오면
`board` 범위를 넘어 `ArrayIndexOutOfBoundsException` 이 난다. Windows에서 만든 입력 파일로
로컬 테스트할 때 실제로 겪을 수 있다. `j < n` 으로 두거나 `readLine().trim()` 을 쓰면 안전하다.

## 복잡도

- 시간: `O(N² log N)` — 칸 N²개, 큐 연산이 각 log. N ≤ 100이라 충분하다.
- 공간: `O(N²)` — `board` + `dist`. 적정하다.

## 요약

네 명 중 가장 짧고(72줄) 가장 정확하다. 죽은 큐 항목을 건너뛰는 처리가 들어간 유일한 코드다.
고칠 건 없고, 입력 파싱만 `n` 기준으로 바꾸면 더 단단해진다.
