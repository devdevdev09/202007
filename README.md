# 202007

## 일일 커밋 체크 프로그램
- api 명세(maven > clean > install)
- http://localhost:20030/docs/api-docs.html

## TODO
- 전년도까지 연속 커밋 체크하기
- REST-DOCS 완성하기
- REST API 설계, ERROR STATUS 반환하기
- 실행 인원 리스트로 입력받기

### 2020-10-26
- ResponseEntity 추가

### 2020-10-23
- spring-rest-docs 추가

### 2020-08-25
- ssl 우회 처리 추가
- 응답 데이터 Map에서 class로 수정

### 2020-08-19
- 년도별 모든 커밋일수 확인
- Exception 에러 메시지 출력

### 2020-08-09
- @Value로 받는 값들 제거
- openjdk08에 맞게 변경(heroku)

### 2020-08-07
- 이전 년도 커밋수 체크
```
GET "/dailycommit/{GITHUB_ID}?year=2020

response
{
    "year" : "2020",
    "commit" : "100",
    "user" : "GITHUB_ID"
}
```
- 전체 프로젝트 소스 정리

### 2020-07-29
- POST로 슬랙 webhook 전달시 해당 슬랙으로 메시지 전송
```
POST "/dailycommit/{GITHUB_ID}"
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