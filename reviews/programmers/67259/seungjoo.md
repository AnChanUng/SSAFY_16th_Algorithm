---
platform: programmers
problemId: "67259"
author: seungjoo
source: 이승주/week3/경주로 건설.java
week: 3
compiles: true
verdict: good
tags: [good-complexity, good-readability, magic-number]
complexity:
  time: O(N^2) 수준 (BFS + 완화)
  space: O(N^2)
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# 경주로 건설 (programmers/67259) — seungjoo

## 접근

`dist[N][N][4]` 3차원 상태에 **BFS + 완화(relaxation)** 를 돌린다.
우선순위 큐 없이 일반 큐로 돌리되, `dist` 가 줄어들 때만 다시 큐에 넣는 방식이다.
이 문제는 간선 비용이 100과 600 두 종류뿐이라 이 방식이 잘 맞고, 힙 오버헤드가 없어서 오히려 빠르다.

시작 처리가 특히 정확하다.

```java
dist[0][0][0] = 0;
dist[0][0][1] = 0;
q.offer(new int[]{x,y,0});
q.offer(new int[]{x,y,1});
```

**오른쪽(dir 0)과 아래(dir 1) 두 방향을 모두 시드로 넣었다.**
한 방향만 넣으면 반대 방향 첫 이동에 커브 비용 500이 잘못 붙는데,
같은 폴더의 실패 버전 주석에 그 시행착오가 그대로 적혀 있다 — 스스로 찾아서 고친 것이다.

**정답 다익스트라와 무작위 판 400개를 대조해 불일치 0건이고, n=25 빈 판에서 1ms다.** `good-complexity`

## 개선점

### 1. (사소) 중괄호 들여쓰기가 깨져 있다 — `good-readability` 를 스스로 깎고 있다

```java
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
    }
}
        dist[0][0][0] = 0;
```

닫는 중괄호 두 개가 열 0에 붙어 있어서, **초기화 루프가 어디서 끝나는지 눈으로 안 잡힌다.**
로직은 맞다. IDE 자동 정렬(IntelliJ `Ctrl+Alt+L`) 한 번이면 끝나는 문제다.

같은 줄에서 `q.offer` 앞에도 공백이 하나 더 붙어 있다.

```java
        q.offer(new int[]{x,y,0});
         q.offer(new int[]{x,y,1});    // 한 칸 밀려 있다
```

### 2. (사소) `static` 필드에 상태를 두고 `this.` 로 대입한다 — `uninitialized-state` 예방

```java
static int N;
static int[][] map;
static int[][][] dist;

public int solution(int[][] board) {
    this.N = board.length;
    this.map = board;
```

`static` 필드를 `this.` 로 대입하는 형태라 컴파일 경고가 난다.
지금은 `bfs` 안에서 `dist` 를 매번 새로 만들기 때문에 동작에 문제가 없다.

다만 **같은 사람이 쓴 `N으로 표현` 풀이에서 정확히 이 구조가 사고를 냈다** —
`static answer` 를 초기화하지 않아 두 번째 테스트케이스부터 전부 오답이 났다.
여기서는 운 좋게 피했지만, 습관 자체를 바꾸는 게 안전하다.

`N`, `map`, `dist` 를 `solution` 의 지역 변수로 두고 `bfs` 에 넘기면 이 위험이 사라진다.

### 3. (사소) `100` 과 `500` 이 식 안에 있다 — `magic-number`

```java
int cost = dist[current[0]][current[1]][current[2]] + 100;
if(current[2] != dir){
    cost += 500;
}
```

`ROAD`, `CORNER` 로 이름을 붙이면 주석 없이 읽힌다.

### 4. (사소) `int[]` 대신 작은 클래스를 쓰면 인덱스를 안 세도 된다

`current[0]`, `current[1]`, `current[2]` 가 각각 행·열·방향인데,
`dist[current[0]][current[1]][current[2]]` 같은 줄은 세 번 다 세어봐야 한다.
주석으로 보완해 둔 건 좋지만(`//dir은 지금 방향, current[2]는 이전 방향`),
이름이 있으면 그 주석이 필요 없어진다.

성능상 `int[]` 가 유리한 건 맞아서 **취향의 문제다.** 지금 규모(N≤25)에서는 차이가 없다.

## 복잡도

- 시간: `O(N^2)` 수준 — 상태 `N*N*4` 개를 완화하며 훑는다. 힙이 없어 상수가 작다
- 공간: `O(N^2)` — `dist` 가 `N*N*4`, 큐도 같은 규모

## 요약

시작 방향 두 개를 시드로 넣는 판단과 3차원 상태 설계가 정확하고,
실패 버전에서 여기까지 스스로 고쳐 온 과정이 파일에 남아 있다. 400판 오답 0건, n=25에서 1ms.
남은 건 정리뿐이다 — 들여쓰기 정렬 한 번과 `static` 을 지역 변수로 내리는 것.
