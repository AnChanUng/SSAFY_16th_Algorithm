---
platform: programmers
problemId: "42579"
author: chanung
source: 안찬웅/week4/베스트앨범.java
week: 4
compiles: true
verdict: needs-fix
tags: [logic-edge-case, redundant-collection, magic-branch]
complexity:
  time: O(N log N)
  space: O(N)
generatedBy: claude-opus-5
generatedAt: 2026-08-16
---

# 베스트앨범 (programmers/42579) — chanung

## 접근

장르별 총 재생수를 `HashMap`에 모으고, `(장르총합 desc, 재생수 desc, 고유번호 asc)` 로 `compareTo` 를
구현한 `Node` 를 우선순위 큐에 전부 넣은 뒤, 하나씩 꺼내며 장르가 바뀌는 순간을 감지해 장르당 2곡씩만 담는다.

정렬 기준 3개를 `compareTo` 하나에 몰아넣은 건 좋다. 문제 조건이 그대로 코드에 드러난다.

## 개선점

### 1. (치명) 장르 총합이 동점이면 장르가 섞여서 3곡 이상 담긴다 — `logic-edge-case`

`while` 루프는 "직전 장르 `str` 과 다르면 새 장르가 시작됐다"고 가정한다.
이 가정은 **같은 장르의 노래가 큐에서 반드시 연달아 나올 때만** 성립한다.
그런데 `compareTo` 의 1순위는 장르 총합이라, 총합이 같은 장르가 둘이면 2순위인 재생수로 섞여 나온다.

실제로 돌려본 반례:

```java
genres = {"a","a","a","b","b"}
plays  = {5,4,1,6,4}       // a 총합 10, b 총합 10 (동점)
```

큐에서 꺼내지는 순서는 `b3, a0, a1, b4, a2` 가 되고, `b4` 에서 장르가 바뀌었다고 판단해 `cnt` 가 1로 리셋된다.
그 뒤 `a2` 도 새 장르로 취급돼 담긴다.

```
실제 출력: [3, 0, 1, 4, 2]   (5곡, a 장르가 3곡)
기대 출력: 장르당 최대 2곡이므로 4곡
```

`str`/`cnt` 라는 "직전 상태" 대신 **장르별 카운터**를 쓰면 순서 가정 자체가 사라진다.

```java
Map<String, Integer> picked = new HashMap<>();
while (!pq.isEmpty()) {
    Node n = pq.poll();
    int c = picked.getOrDefault(n.genres, 0);
    if (c < 2) {
        list.add(n.idx);
        picked.put(n.genres, c + 1);
    }
}
```

### 2. `cnt <= 1` / `cnt >= 2` 3분기는 카운터 하나면 사라진다 — `magic-branch`

`if (str.equals("")) / else if (같고 cnt<=1) / else if (같고 cnt>=2) / else` 4갈래는
위 1번 수정본에서 `if (c < 2)` 한 줄이 된다. 첫 진입을 위한 `str.equals("")` 특수 처리도 필요 없어진다.
빈 문자열을 "아직 없음"의 표식으로 쓰는 건 장르 이름이 `""` 인 입력이 오면 깨지기도 한다.

### 3. `sing` 채우는 루프는 `merge` 한 줄 — `redundant-collection`

```java
sing.merge(genres[i], plays[i], Integer::sum);
```

`containsKey` → `get` → `put` 은 해시 조회를 3번 한다. `merge` 는 1번이다.

### 4. `Node.genres` 필드명이 단수/복수 혼동을 부른다 — `naming`

파라미터 `genres` 는 배열 전체, 필드 `genres` 는 장르 하나다. 필드는 `genre` 가 맞다.
같은 이유로 `genresCount` 는 실제로는 "장르 노래 수"가 아니라 **장르 총 재생수**다 (주석과 값이 다르다).
`genreTotalPlays` 처럼 값이 뭔지 드러나는 이름을 권한다.

### 5. `Node` 는 `static` 중첩 클래스로 — `inner-class`

현재 `Node` 는 내부 클래스라 인스턴스마다 바깥 `Solution` 참조를 들고 다닌다.
`solution` 이 인스턴스 메서드라 컴파일은 되지만, `static class Node` 가 의도에 맞고 메모리도 덜 쓴다.

## 복잡도

- 시간: `O(N log N)` — 전체 노래를 PQ에 넣고 빼는 비용이 지배적. 적정하다.
- 공간: `O(N)` — PQ + list. 적정하다.

## 요약

정렬 기준을 `compareTo` 로 표현한 뼈대는 좋다. 다만 "같은 장르는 연달아 나온다"는 암묵적 가정이
장르 총합 동점에서 무너져 오답이 된다. 상태 변수(`str`, `cnt`) 대신 장르별 카운터 맵으로 바꾸면
버그와 4갈래 분기가 동시에 없어진다.
