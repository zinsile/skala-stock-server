```
skala-stock-console/
├── 📁 app/                     # 애플리케이션 설정 관련 디렉터리
├── 📁 build/                   # 빌드 결과물
├── 📁 data/                    # 텍스트 기반 주식/플레이어 데이터
│   ├── players                # 사용자 데이터
│   └── stocks                 # 주식 데이터
├── 📁 gradle/                  # Gradle 설정 관련 파일
├── 📁 src/                     # Java 백엔드 소스코드
│   └── main/
│       └── java/
│           ├── App.java
│           ├── Player.java
│           ├── PlayerRepository.java
│           ├── PlayerStock.java
│           ├── SkalaStockMarket.java
│           ├── Stock.java
│           ├── StockRepository.java
│           └── WebServer.java
├── 📁 vue-stock-dashboard/     # Vue 프론트엔드 대시보드
│   ├── 📁 node_modules/        # Node.js 의존성
│   ├── 📁 public/              # 정적 리소스
│   ├── 📁 src/
│   │   ├── 📁 assets/          # 이미지, 폰트 등
│   │   ├── 📁 components/     
│   │   │   ├── PlayerList.vue
│   │   │   └── StockList.vue
│   │   ├── App.vue
│   │   ├── main.js
│   │   └── style.css
│   ├── .gitignore
│   ├── index.html
│   ├── package.json
│   ├── package-lock.json
│   ├── README.md
│   └── vite.config.js
├── .gitignore
├── .gitattributes
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

