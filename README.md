# 202007

## 일일 커밋 체크 프로그램

## TODO
- index.html 수정하기(설명 보충)
- spring cache 사용하기
- 외부 호출은 hook url 있으면 슬랙 전송, 없으면 응답만, 스케줄러에선 슬랙에 전송하기
- REST API 설계, ERROR STATUS 반환하기
- API 인증 추가하기
- 실행 인원 리스트로 입력받기
- 프로젝트 구조 변경하기
- jenkins 앱 실행 스크립트 작성
- 1년 이상 날짜는 연속일 체크 불가(현재로는) > 방법 알아보기

### 2020-07-29
- POST로 슬랙 webhook 전달시 해당 슬랙으로 메시지 전송
```
POST "/dailycommit/GITHUB_ID"
{ 
    "webhook" : "SLACK_WEBHOOK_URL"
}
```

### 2020-07-28
- heorku log 테스트
- heroku no sleep test(spring scheduler)

### 2020-07-27
- index.html 추가
- @Controller, @RestController 분리

### 2020-07-26
- heroku 연결(https://daily-commit-counter.herokuapp.com)

### 2020-07-23
- hooks url 실행시 넘기게 변경
    - (VSCODE > launch.json) "vmArgs": "-Dslack.value.hooks=SLACK_HOOK_URL"

### 2020-07-20
- README.md 역순으로 변경
- log 설정 추가(application.properties)
- pom.xml 추가
- Slack 메시지 전송 추가
- restUtils, common-utils 추가
- 날짜 역순으로 연속일 체크

### 2020-07-19
- 프로젝트 이름 추가
- 배너 추가(DAILY COMMIT-COUNTER)
- 스프링 스케줄러 추가

### 2020-07-09
- jenkins 배포용 sample 프로젝트 생성

### 2020-07-08
- 저장소 생성