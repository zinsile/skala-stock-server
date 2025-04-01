# SKALA Stock Market Simulation

이 저장소는 주식 거래 시뮬레이션 프로젝트를 담고 있습니다.

## 브랜치 정보

### v1 브랜치
- 순수 Java로 개발된 주식 거래 시뮬레이션
- Vue 프론트엔드 코드 내장
- 기본 기능 구현

### v2 브랜치
- v1을 Spring Boot로 리팩토링
- 데이터베이스 연결 없이 텍스트 파일로 데이터 저장/로드
- RESTful API 구현

### v3 브랜치
- v2에서 JPA를 이용한 데이터베이스 연결 추가
- DTO 구조 리팩토링
- 개선된 API 구조

## 웹 인터페이스

v3 브랜치의 경우 [skala-stock-vue](https://github.com/zinsile/skala-stock-vue) 저장소의 프론트엔드와 함께 사용할 수 있습니다.

자세한 정보 및 설치 방법은 v3 브랜치의 README.md를 참고하세요.

## 시작하기

각 브랜치로 전환하여 해당 버전의 코드를 확인할 수 있습니다:

```bash
# v1 브랜치 (순수 Java)
git checkout v1

# v2 브랜치 (Spring Boot + 텍스트 파일 저장)
git checkout v2

# v3 브랜치 (Spring Boot + JPA + 데이터베이스)
git checkout v3
```