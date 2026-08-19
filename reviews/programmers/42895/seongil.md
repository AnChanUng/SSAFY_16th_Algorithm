---
platform: programmers
problemId: "42895"
author: seongil
source: 이성일/week3/N으로표현.java
week: 3
compiles: true
verdict: needs-fix
tags: [dead-code, magic-number, good-complexity, good-decomposition]
complexity:
  time: O(8^2 * |dp|^2)
  space: O(sum |dp[i]|)
generatedBy: claude-code-local
generatedAt: 2026-08-19
---

# N으로 표현 (programmers/42895) — seongil

## 접근

개수별 집합 DP. `amplify(left, right, len, ...)` 로 **두 집합을 합치는 일을 별도 메서드로 뽑아낸**
구조가 좋다 — `solution` 은 "len 을 늘리며 분할을 훑는다"만 남고 연산 세부는 안 보인다. `good-decomposition`

세 가지 판단이 특히 정확하다.

1. `opResult > 32000 || opResult <= 0` 으로 **쓸모없는 값을 바로 버린다.** 집합이 안 불어나고
   오버플로 위험도 줄어든다. 같은 문제를 푼 다른 팀원 코드에는 이 가지치기가 없다.
2. `Math.abs(n - m)` 과 `Math.max(n,m) / Math.min(n,m)` — 뺄셈과 나눗셈의 **두 순서를 한 번에 처리**한다.
   음수와 0은 어차피 위에서 걸러지므로 `(left, right)` 를 한쪽 방향만 훑어도 손실이 없다.
   `while (left <= right)` 로 절반만 도는 게 이 덕분에 성립한다.
3. 이어붙인 수(`concatNum`)는 32000 필터를 거치지 않고 넣는다. `33333 / 3 = 11111` 같은 경로가
   살아남아야 하므로 이게 맞다.

**정답 DP와 540케이스를 대조해 불일치 0건이다.** `good-complexity`

## 개선점

### 1. (사소) `amplify` 의 `number` 파라미터가 안 쓰인다 — `dead-code`

```java
static void amplify(int left, int right, int len, int number) {
```

본문 어디서도 `number` 를 읽지 않는다. 실패 버전(`N으로표현FailVersion.java`)에서는
`opResult == number` 를 여기서 검사하고 `boolean` 을 돌려줬는데, 정답 버전으로 옮기면서
그 로직만 빠지고 파라미터가 남았다. 지우면 호출부도 같이 짧아진다.

```java
static void amplify(int left, int right, int len) {
```

### 2. (사소) `Math.pow` 로 자릿수를 만든다 — `magic-number`

```java
concatNum += N * (int) (Math.pow(10, i));
```

`Math.pow` 는 `double` 연산이다. 여기서는 `i <= 7` 이라 `10^7 = 1e7` 까지고 double 정밀도 안이라
결과가 맞지만, **정수 자릿수를 double 로 만드는 습관은 큰 자릿수에서 1 차이로 어긋난다.**
정수만으로 누적하면 그 걱정이 없고 더 짧다.

```java
int concatNum = 0;
for (int i = 0; i < len; i++) concatNum = concatNum * 10 + N;
```

### 3. (사소) 배열이 `static` 인데 `solution` 안에서 초기화된다 — `magic-number`

```java
static Set<Integer>[] dp = new HashSet[9];
```

`solution` 첫머리에서 매번 `new HashSet<>()` 로 채우고 있어서 지금은 문제가 없다.
다만 **초기화를 한 줄만 빠뜨려도 이전 호출 값이 그대로 남는** 구조다.
같은 문제 `이승주` 풀이가 정확히 그 사고를 냈다 (`static answer` 미초기화 → 첫 케이스 이후 전부 오답).

`solution` 안의 지역 변수로 내리면 그 위험 자체가 사라진다.
`amplify` 에 넘겨야 해서 `static` 으로 둔 것이라면, 파라미터로 받는 편이 안전하다.

또 `new HashSet[9]` 는 raw type 이라 `unchecked` 경고가 난다.
`List<Set<Integer>>` 로 두면 경고가 없어진다.

### 4. (사소) `len` 루프의 시작이 1이라 첫 바퀴가 사실상 빈다

`len = 1` 일 때 `left=1, right=0` 이라 `while (left <= right)` 가 한 번도 안 돈다.
`concatNum` 계산과 `dp[1].add(N)` 중복 추가만 하고 지나간다.
`N == number` 인 경우를 잡아주는 역할은 하지만, 의도가 코드에 드러나지 않는다.
`len = 2` 부터 돌리고 그 앞에 `if (N == number) return 1;` 을 두면 읽기 쉬워진다.

## 복잡도

- 시간: `O(8 * 8 * |dp|^2)` — 32000 가지치기 덕에 `|dp[i]|` 가 실질적으로 묶여 있다
- 공간: `O(sum |dp[i]|)` — 32000 이하로 잘라서 담으므로 예측 가능하다

## 요약

이 문제의 정석 DP를 정확히 구현했고, 32000 가지치기와 `abs`/`max-min` 으로
탐색 절반을 접은 판단이 특히 좋다. 540케이스 대조 오답 0건.
남은 건 전부 정리 수준 — 안 쓰는 파라미터, `Math.pow`, 불필요한 `static` 세 가지다.
