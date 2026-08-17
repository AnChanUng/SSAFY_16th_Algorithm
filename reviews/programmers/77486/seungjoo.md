---
platform: programmers
problemId: "77486"
author: seungjoo
source: 이승주/week2/다단계 칫솔 판매.java
week: 2
compiles: true
verdict: needs-fix
tags: [uninitialized-state, duplicate-code, dead-code, good-complexity]
complexity:
  time: O(N + S · D)
  space: O(N + S)
generatedBy: claude-code-local
generatedAt: 2026-08-17
---

# 다단계 칫솔 판매 (programmers/77486) — seungjoo

## 접근

이름 → 인덱스 맵을 만들고, 매출마다 `dfs(주는 사람, 받는 사람, 금액)` 재귀로 체인을 올라간다.
답은 맞고 복잡도도 좋다.

인자 이름에 주석으로 역할을 달아둔 게 읽기에 도움이 된다.

```java
dfs(seller[i], person, profit[i]); //금액을 나눠주는 사람, 금액을 받아야하는 사람, 전체 금액
```

`parent` 배열(= `referral`)로 한 칸 위를 찾는 구조도 명확하다.

## 개선점

### 1. (중요) 상태를 `static` 으로 들고 있다 — `uninitialized-state`

```java
static HashMap<String, Integer> map;
static String[] parent;
static int[] profit;
static int[] result;

public int[] solution(...) {
    this.parent = referral;   // static 필드를 this 로 대입
```

두 가지가 문제다.

**(a) `this.parent = referral`** — `parent` 는 `static` 인데 인스턴스 참조로 대입하고 있다.
컴파일은 되지만 "인스턴스마다 따로"라는 오해를 부른다. 실제로는 클래스 전체가 공유한다.

**(b) 채점기가 같은 JVM에서 여러 번 호출하면 이전 호출의 값이 남는다.**
지금은 `solution` 진입 시 네 개 모두 새로 대입해서 우연히 안전하지만,
필드 하나만 초기화를 빠뜨려도 **앞 테스트케이스의 결과가 다음 케이스에 섞여 들어간다.**
찾기 매우 어려운 종류의 버그다.

`dfs` 에 필요한 것만 인자로 넘기거나, 필드를 쓰더라도 `static` 을 떼고 인스턴스 필드로 두는 게 안전하다.

### 2. (중요) "90% 갖고 10% 올려보낸다"가 세 군데 흩어져 있다 — `duplicate-code`

같은 규칙이 세 곳에 다른 모양으로 적혀 있다.

```java
// (1) solution — 판매자의 추천인이 "-" 인 경우
result[index] += profit[i] - profit[i]/10;

// (2) dfs — 10%가 1원 미만인 경우
result[map.get(from)] += cost;

// (3) dfs — 받는 사람의 추천인이 "-" 인 경우
result[toIndex] += money - money/10;
```

세 개가 다 맞긴 한데, 규칙이 바뀌면 세 군데를 모두 고쳐야 하고 하나만 놓쳐도 조용히 틀린다.
**"사람"을 기준으로 한 함수로 통일**하면 분기가 하나로 줄어든다.

```java
// person 이 money 를 벌었을 때
static void distribute(String person, int money) {
    int up = money / 10;
    int idx = map.get(person);
    if (up < 1 || parent[idx].equals("-")) {
        result[idx] += money;          // 전액 본인 (위로 갈 게 없거나 1원 미만)
        return;
    }
    result[idx] += money - up;         // 90% 본인
    distribute(parent[idx], up);       // 10%만 위로
}

// 호출부
distribute(seller[i], amount[i] * 100);
```

`solution` 의 `if/else` 도, `dfs` 의 세 갈래도 전부 사라진다.

현재 구조가 복잡해진 이유는 `dfs(from, to, cost)` 가 **두 사람을 동시에** 다루기 때문이다.
한 사람만 다루면 "받는 사람의 추천인이 `-` 인가"를 미리 볼 필요가 없어진다.

### 3. (사소) 도달하지 않는 `return` 이 있다 — `dead-code`

```java
    } else {
        result[toIndex] += money - money/10;
        return;              // else 블록 끝 -> 불필요
    }
}
return;                       // 메서드 끝 (void) -> 불필요
```

`void` 메서드의 마지막 `return;` 과 블록 끝 `return;` 은 지워도 동작이 같다.

### 4. (사소) `profit` 배열은 필요 없다

```java
profit = new int[amount.length];
for(int i = 0; i < seller.length; i++){
    profit[i] = amount[i]*100;
}
```

`amount[i] * 100` 을 쓰는 곳이 한 군데뿐이라 배열로 미리 만들 이유가 없다.
크기를 `amount.length` 로 잡고 `seller.length` 로 채우는 것도 어긋나 있다
(제약상 두 값이 같아서 지금은 문제없다).

## 복잡도

- 시간: `O(N + S · D)` — 맵 생성 N, 매출마다 체인 깊이 D. 좋다.
- 공간: `O(N + S)` — `profit` 을 없애면 `O(N)`.

## 요약

접근과 복잡도는 좋다. 다만 `dfs` 가 "주는 사람"과 "받는 사람"을 함께 들고 다니면서
같은 규칙이 세 군데로 갈라졌다. 사람 하나를 기준으로 재귀를 다시 쓰면 세 분기가 하나로 합쳐지고,
`static` 상태도 같이 줄일 수 있다.
