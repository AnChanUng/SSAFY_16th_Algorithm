---
platform: programmers
problemId: "42895"
author: seungjoo
source: 이승주/week3/N으로 표현.java
week: 3
compiles: true
verdict: wrong
tags: [uninitialized-state, wrong-algorithm, time-complexity]
complexity:
  time: O(32^8) — 가지치기가 없다
  space: O(8) 재귀 깊이
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# N으로 표현 (programmers/42895) — seungjoo

## 접근

`dfs(cnt, value)` 로 "지금까지 N을 `cnt` 개 써서 `value` 를 만들었다"를 들고 다니며,
다음에 붙일 수 `num`(= `N`, `NN`, `NNN`…)을 골라 사칙연산 네 가지로 갈라진다.
탐색 깊이를 `cnt + i <= 8` 로 자른 것과, 재귀 한 줄로 이어붙인 수를 만드는 `num = num * 10 + N` 은 깔끔하다.

다만 정답 DP와 대조한 결과 **540케이스 중 531건이 틀린다.** 원인이 두 개 겹쳐 있다.

## 개선점

### 1. (치명) `static answer` 가 호출 사이에 초기화되지 않는다 — `uninitialized-state`

```java
static int answer = Integer.MAX_VALUE;
static int N;
static int target;

public int solution(int N, int number) {
    this.N = N;
    this.target = number;
    dfs(0, 0);
    return answer == Integer.MAX_VALUE ? -1 : answer;
}
```

`answer` 는 `static` 이라 **인스턴스를 새로 만들어도 이전 호출 값이 그대로 남는다.**
`dfs` 안의 `answer = Math.min(answer, cnt)` 는 값을 줄이기만 하므로,
한 번 작은 값이 들어가면 이후 모든 케이스가 그 값에 눌린다.

실제로 돌려본 반례 (같은 JVM에서 연달아 호출):

```
solution(1, 1)  ->  1     (여기서 answer 가 1로 굳는다)
solution(1, 2)  ->  1     기대 2
```

리플렉션으로 `answer` 를 매 호출마다 `Integer.MAX_VALUE` 로 되돌리고 다시 재면
불일치가 **531건에서 156건으로 줄어든다.** 나머지 156건은 2번이 원인이다.

`solution` 첫머리에서 초기화하면 이 결함은 사라진다.

```java
public int solution(int N, int number) {
    answer = Integer.MAX_VALUE;
    Solution.N = N;
    Solution.target = number;
    ...
}
```

`this.N = N` 은 **static 필드를 인스턴스 참조로 대입**하는 형태라 컴파일 경고가 난다.
static 으로 둘 거면 `Solution.N` 으로 쓰거나, 아예 static 을 떼고 인스턴스 필드로 만드는 게 맞다.

### 2. (치명) 괄호가 없다 — 왼쪽부터 순서대로 붙이는 식만 만든다 — `wrong-algorithm`

`dfs(cnt + i, value + num)` 처럼 **항상 `value` 가 왼쪽, 새로 만든 `num` 이 오른쪽**이다.
즉 `((a op b) op c) op d` 형태만 만들어지고, `(a op b) op (c op d)` 를 만들 수 없다.

실제로 돌려본 반례 (static 초기화 문제를 제거한 상태):

```
solution(1, 5)   기대 4   실제 5
```

정답 경로는 `11 / (1 + 1) = 5` 다. 오른쪽 피연산자가 `1 + 1` 이라는 **조합된 값**이라
이 탐색으로는 도달할 수 없다. `1+1+1+1+1 = 5` 로 5개를 쓰는 경로밖에 못 찾는다.

`num - value`, `num / value` 처럼 **좌우를 뒤집은 경우**도 빠져 있다.

이건 가지 하나를 추가해서 메우는 종류가 아니라 탐색 구조를 바꿔야 한다.
개수별 집합 DP로 가면 좌우 조합이 자연히 다 나온다.

```java
List<Set<Integer>> dp = new ArrayList<>();   // dp.get(i) = N을 i개 써서 만드는 값들
for (int i = 1; i <= 8; i++) {
    dp.get(i).add(repunit(N, i));
    for (int j = 1; j < i; j++)                       // 왼쪽 j개 + 오른쪽 (i-j)개
        for (int a : dp.get(j)) for (int b : dp.get(i - j)) {
            // a+b, a-b, a*b, a/b (b != 0) 를 dp.get(i) 에 넣는다
        }
    if (dp.get(i).contains(number)) return i;         // 처음 나온 i 가 곧 최소
}
return -1;
```

같은 문제의 `안찬웅`·`이성일` 풀이가 이 구조이고, 둘 다 540케이스 대조에서 불일치 0건이다.

### 3. (중요) 가지치기가 하나도 없다 — `time-complexity`

`value` 가 32000을 넘어가도, 이미 찾은 `answer` 보다 `cnt` 가 커져도 계속 파고든다.
한 노드가 최대 `8 * 4 = 32` 갈래이고 깊이가 8이라 최악 `32^8` 이다.

`if (cnt >= answer) return;` 한 줄만 넣어도 체감이 크게 달라진다.
다만 2번을 고치면 DP로 바뀌므로 이 항목은 자연히 없어진다.

### 4. (사소) `if (num != 0)` 은 항상 참이다

`num = num * 10 + N` 이고 `N >= 1` 이라 `num` 이 0이 되는 경우가 없다.
나눗셈에서 막아야 할 건 `num` 이 아니라 **오른쪽 피연산자가 0이 되는 경우**인데,
지금 구조에서는 그게 `num` 이라 우연히 맞아떨어졌다. 구조를 바꾸면 이 검사 위치도 바뀐다.

## 복잡도

- 시간: `O(32^8)` — 가지치기가 없어 상태가 중복 탐색된다. 지배적인 건 `dfs` 분기
- 공간: `O(8)` — 재귀 깊이만큼. 이건 문제없다

## 요약

`num = num * 10 + N` 으로 이어붙인 수를 만드는 부분과 깊이 제한은 깔끔한데,
`static answer` 미초기화와 "괄호 없는 왼쪽 결합"이라는 두 결함이 겹쳐 540케이스 중 531건이 틀린다.
초기화는 한 줄로 고쳐지지만, 두 번째는 개수별 집합 DP로 갈아타야 한다.
