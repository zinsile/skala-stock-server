# SKALA-STOCK-MARKET

## 프로젝트 소개
이 프로젝트는 **Spring Boot** 기반의 주식 거래 애플리케이션입니다. 플레이어가 가상의 주식을 사고팔 수 있는 시뮬레이션 환경을 제공합니다.

---

## 주요 기능

- 플레이어 생성, 조회, 삭제
- 주식 추가 및 가격 업데이트
- 주식 구매 및 판매
- 플레이어 자금 관리
- 플레이어 보유 주식 조회

---

## 프로젝트 구조

```
SKALA-STOCK-CONSOLE/
├── .gradle/
├── .vscode/
├── bin/
├── build/
├── data/                   # 데이터 저장 디렉토리
├── gradle/
├── src/main/
│   ├── java/com/skala/
│   │   ├── config/         # 애플리케이션 설정
│   │   │   └── AppConfig.java
│   │   ├── controller/     # REST API 컨트롤러
│   │   │   ├── PlayerController.java
│   │   │   └── StockController.java
│   │   ├── model/          # 데이터 모델
│   │   │   ├── Player.java
│   │   │   ├── PlayerStock.java
│   │   │   └── Stock.java
│   │   ├── repository/     # 데이터 관리 계층
│   │   │   ├── PlayerRepository.java
│   │   │   ├── PlayerRepositoryHelper.java
│   │   │   └── StockRepository.java
│   │   ├── service/        # 비즈니스 로직 계층
│   │   │   ├── PlayerService.java
│   │   │   ├── PlayerServiceInterface.java
│   │   │   ├── StockService.java
│   │   │   └── StockServiceInterface.java
│   │   └── App.java       # 애플리케이션 진입점
│   └── resources/         # 리소스 파일
├── .gitattributes
├── .gitignore
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── package-lock.json
├── README.md
└── settings.gradle
```

---

## 기술 스택

- Java
- Spring Boot
- Gradle
- RESTful API

---

## 설치 및 실행 방법

### 1. 프로젝트 clone
```bash
git clone https://github.com/username/SKALA-STOCK-CONSOLE.git
```

### 2. 프로젝트 디렉토리로 이동
```bash
cd SKALA-STOCK-CONSOLE
```

### 3. Gradle을 사용하여 빌드
```bash
./gradlew build
```

### 4. 애플리케이션 실행
```bash
./gradlew bootRun
```

---

## API 엔드포인트

### 플레이어 API

| Method | Endpoint                                                         | Description                      |
|--------|------------------------------------------------------------------|----------------------------------|
| GET    | `/api/players`                                                   | 모든 플레이어 조회                 |
| GET    | `/api/players/{id}`                                              | ID로 플레이어 조회                 |
| POST   | `/api/players?id={id}`                                           | 새 플레이어 생성                   |
| DELETE | `/api/players/{id}`                                              | 플레이어 삭제                      |
| POST   | `/api/players/{id}/money?amount={amount}`                        | 플레이어 자금 추가                 |
| POST   | `/api/players/{playerId}/buy?stockName={stockName}&quantity={quantity}`  | 주식 구매                        |
| POST   | `/api/players/{playerId}/sell?stockName={stockName}&quantity={quantity}` | 주식 판매                        |
| GET    | `/api/players/{playerId}/stocks`                                 | 플레이어 보유 주식 조회             |
| GET    | `/api/players/{playerId}/stocks/menu`                            | 플레이어 보유 주식을 메뉴 형식으로 조회 |

### 주식 API

| Method | Endpoint                                     | Description                   |
|--------|----------------------------------------------|-------------------------------|
| GET    | `/api/stocks`                                | 모든 주식 조회                  |
| GET    | `/api/stocks/{name}`                         | 이름으로 주식 조회              |
| POST   | `/api/stocks?name={name}&price={price}`      | 새 주식 추가                    |
| PUT    | `/api/stocks/{name}?price={price}`           | 주식 가격 업데이트              |
| GET    | `/api/stocks/menu`                           | 메뉴 형식으로 주식 목록 조회     |

---

## 데이터 구조

데이터는 **`data`** 디렉토리에 텍스트 파일 형태로 저장됩니다.

- **players.txt** - 플레이어 정보
- **stocks.txt** - 주식 정보

---

## 작성자

**진실**

