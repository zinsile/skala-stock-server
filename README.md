# Skala 주식 거래 시뮬레이션

Skala 주식 거래 시뮬레이션은 사용자가 가상의 주식을 사고 팔며 투자 전략을 연습할 수 있는 웹 애플리케이션입니다.

이 프로젝트는 **Spring Boot 백엔드** 와 **Vue.js 프론트엔드** 구조로 구성되어 있습니다.

---

## 해당 기능

### 플레이어
- 플레이어 생성 및 관리
- 투자 자금 추가
- 플레이어별 보유 주식 조회
- 플레이어 탈퇴

### 주식
- 주식 목록 조회
- 새 주식 종목 추가
- 주식 매수 및 매도

---

## 기술 스택

### 백엔딩
- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- Jakarta Persistence API
- Lombok

### 프론트엔딩
- Vue.js 3
- Axios
- CSS (컨퍼런트 스타일링)

---

## 프로젝트 구조

### 백엔딩 구조
```plaintext
com.skala
├── config
│   ├── AppConfig.java
│   └── DataInitializer.java
├── controller
│   ├── PlayerController.java
│   └── StockController.java
├── dto
│   ├── common
│   │   └── ResponseDto.java
│   ├── player
│   │   ├── PlayerRequestDto.java
│   │   └── PlayerResponseDto.java
│   └── stock
│       ├── StockRequestDto.java
│       └── StockResponseDto.java
├── model
│   ├── Player.java
│   ├── PlayerStock.java
│   └── Stock.java
├── repository
│   ├── PlayerRepository.java
│   ├── PlayerStockRepository.java
│   └── StockRepository.java
├── service
│   ├── PlayerService.java
│   ├── PlayerServiceInterface.java
│   ├── StockService.java
│   └── StockServiceInterface.java
└── App.java
```

### 프론트엔딩 구조
```plaintext
src
├── assets
├── components
│   ├── PlayerList.vue
│   └── StockList.vue
├── App.vue
├── main.js
└── style.css
```

---

## 설치 및 실행 방법

### 백엔딩
```bash
# 프로젝트 클론하기
git clone https://github.com/username/skala-stock-server.git
cd skala-stock-server

# 백엔딩 빌드 및 실행
gradlew bootRun # (window)
./gradlew bootRun # (mac)
# 또는 IDE에서 App.java 실행
```

### 프론트엔딩
```bash
# Vue.js 프로젝트 디렉토리로 이동
git clone ~

# 의종성 설치
npm install

# 개발 서버 실행
npm run dev

# 브라우저에서 조회
http://localhost:5173/
```

---

## API 엔드포인트

- swagger를 통한 API 명세
http://localhost:8080/swagger-ui/index.html
(본인 로컬호스트로 들어가면 됌)

### 플레이어 관리
```
GET    /api/players                         - 모든 플레이어 조회
GET    /api/players/{id}                   - ID로 플레이어 조회
POST   /api/players                         - 새 플레이어 생성
DELETE /api/players/{id}                   - 플레이어 삭제
POST   /api/players/{id}/money             - 플레이어 자금 추가
POST   /api/players/{playerId}/buy         - 주식 구매
POST   /api/players/{playerId}/sell        - 주식 매도
GET    /api/players/{playerId}/stocks      - 보유 주식 조회
GET    /api/players/{playerId}/stocks/menu - 메뉴 형식으로 보유 주식 조회
```

### 주식 관리
```
GET  /api/stocks              - 모든 주식 조회
GET  /api/stocks/{name}      - 이름으로 주식 조회
POST /api/stocks             - 새 주식 추가
PUT  /api/stocks/{name}      - 주식 가격 업데이트
GET  /api/stocks/menu        - 메뉴 표시용 주식 목록 조회
```

---

## 데이터베이스 구조

이 프로젝트는 **H2 인메리 데이터베이스**를 사용하며, 다음과 같은 테이블 구조를 가진다:

### players 테이블
| 속성 | 설명 |
|-----------|--------|
| `player_id (PK)` | 플레이어 ID |
| `player_money`   | 플레이어 보유 자금 |

### stocks 테이블
| 속성 | 설명 |
|-----------|--------|
| `stock_name (PK)` | 주식 이름 |
| `stock_price`     | 주식 현재 가격 |

### player_stocks 테이블
| 속성 | 설명 |
|-----------|--------|
| `id (PK)`            | 자동 생성 ID |
| `player_id (FK)`     | 플레이어 ID 참조 |
| `stock_name`         | 주식 이름 |
| `stock_price`        | 구매 당시 가격 |
| `stock_quantity`     | 보유 수량 |

---

## 초기 데이터

애플리케이션 시작 시 다음과 같은 기본 주식 데이터가 초기화됩니다:

- **SKALA** (250000원)
- **Tesla** (350000원)
- **Samsung** (150000원)

