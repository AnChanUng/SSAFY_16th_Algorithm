### 규칙

1. 하루 1문제 올리기 (주말 포함)
2. 매주 3회 코드리뷰 </br>
2.1 오전 8:30 코드리뷰 (화, 목) </br>
2.2 시간빌 때, 1시간 코드리뷰 (월, 수, 금) 中 1회
3. 코드리뷰 받고 싶을 때 PR 올리기

### 디렉토리 구조

모든 작업은 **본인 폴더** 내에서 진행합니다.

```
[이름]/
└── [week1]/
    └── SWEA1767.java
```

### git

```bash
git pull origin main
git commit -m "20260724 안찬웅 SWEA1767"
git push origin main
```

### 목표
 
1. 삼성 소프트웨어 역량테스트 A형 취득
2. 삼성 소프트웨어 역량테스트 B형 취득
3. 삼성 소프트웨어 역량테스트 C형 취득

---

## 비교 사이트 & 리뷰 도구

**풀이 파일은 지금까지 하던 대로 올리면 됩니다.** 폴더 구조도 커밋 메시지도 그대로입니다.
아래는 그 위에 얹은 도구입니다.

### 사이트 보기

`site/index.html` 을 더블클릭하면 열립니다. 서버도 설치도 필요 없습니다.

같은 문제를 푼 사람들의 코드가 3열로 나란히 뜨고, 리뷰가 있으면 코드 아래에 붙습니다.
상단 탭에서 **사람별**(반복 지적 패턴 Top 5), **로테이션**(미제출 현황)도 볼 수 있습니다.

### 리뷰 달기 — Claude Code 에서

```
/review 안찬웅/week4/베스트앨범.java
```

`reviews/programmers/42579/chanung.md` 가 생깁니다. 커밋하면 사이트에 반영됩니다.

어디부터 할지 모르겠으면 리뷰 없는 풀이 목록을 뽑아줍니다.

```bash
node scripts/scan.mjs --reviews
```

### 발표 후 팀 피드백

`reviews/{platform}/{problemId}/team-feedback.md` 에 마크다운으로 적으면
문제 상세 화면 맨 위에 뜹니다. 형식 제약 없습니다.

### 새 문제를 풀었는데 사이트에 안 나올 때

파일명이 `data/problems.json` 에 등록되어 있어야 인식됩니다. 한 줄 추가하면 됩니다.

```bash
node scripts/check.mjs     # 뭐가 빠졌는지 알려줍니다
```

### 데이터 고친 뒤

```bash
node scripts/build-site.mjs   # 사이트 번들 다시 만들기
```

### CI

`main`/`chanung` 에 push 하면 자동으로 돕니다. **API 키나 Secret 은 필요 없습니다.**

- **컴파일 체크** — 이번에 바뀐 `.java` 만 `javac` 로 확인합니다.
  기존에 깨진 파일 때문에 빨간불이 뜨지는 않습니다.
- **사이트 배포** — `main` 에서만 GitHub Pages 로 나갑니다.
  (저장소 Settings → Pages → Source 를 `GitHub Actions` 로 바꿔야 동작합니다)
