// package com.skala;
// /*
//  * <작성자>
//  * 진실
//  * 
//  * <최종 업데이트 날짜>
//  * 2025.03.27
//  * 
//  * <클래스 개요>
//  * 스칼라 주식 프로그램의 웹 서버를 구성하는 클래스 (Spark Java 사용)
//  * 
//  * - HTTP API를 통해 주식 정보, 플레이어 정보, 거래 기능을 외부에 제공
//  * - CORS 허용 설정 포함
//  * 
//  * 1. main(String[] args) : 웹 서버 실행, 라우팅 설정
//  * 2. /api/stocks (GET) : 전체 주식 목록 조회
//  * 3. /api/players (GET) : 전체 플레이어 목록 조회
//  * 4. /api/buy (POST) : 플레이어의 주식 구매 처리
//  * 5. /api/sell (POST) : 플레이어의 주식 판매 처리
//  * 6. /api/removePlayer (POST) : 플레이어 탈퇴 처리
//  * 7. /api/addStock (POST) : 새로운 주식 종목 추가
//  * 8. cors() : CORS 허용 설정
//  */

//  import com.google.gson.Gson;
// import com.skala.controller.SkalaStockMarket;
// import com.skala.model.Player;

// import static spark.Spark.before;
//  import static spark.Spark.get;
//  import static spark.Spark.options;
//  import static spark.Spark.port;
//  import static spark.Spark.post;
 
//  public class WebServer {
//      static Gson gson = new Gson();                      // JSON 변환용 Gson 객체
//      static SkalaStockMarket market = new SkalaStockMarket();  // 핵심 비즈니스 로직 객체
 
//      // 1. 웹 서버 실행 및 라우터 설정
//      public static void main(String[] args) {
//          port(8080);   // 서버 포트 설정
//          cors();       // CORS 설정
 
//          // 2. 전체 주식 목록 조회 API
//          get("/api/stocks", (req, res) -> {
//              res.type("application/json");
//              return gson.toJson(market.getStocks());
//          });
 
//          // 3. 전체 플레이어 목록 조회 API
//          get("/api/players", (req, res) -> {
//              res.type("application/json");
//              return gson.toJson(market.getPlayers());
//          });
 
//          // 4. 주식 구매 API
//          post("/api/buy", (req, res) -> {
//              String player = req.queryParams("player");
//              String stock = req.queryParams("stock");
//              int quantity = Integer.parseInt(req.queryParams("quantity"));
//              market.buyStock(player, stock, quantity);
//              return "success";
//          });
 
//          // 5. 주식 판매 API
//          post("/api/sell", (req, res) -> {
//              String player = req.queryParams("player");
//              String stock = req.queryParams("stock");
//              int quantity = Integer.parseInt(req.queryParams("quantity"));
//              market.sellStock(player, stock, quantity);
//              return "success";
//          });
 
//          // 6. 플레이어 탈퇴 API
//          post("/api/removePlayer", (req, res) -> {
//              String playerId = req.queryParams("player");
//              boolean removed = market.removePlayer(playerId);
//              if (removed) {
//                  return "Player removed successfully.";
//              } else {
//                  res.status(404);
//                  return "Player not found.";
//              }
//          });
 
//          // 7. 주식 종목 추가 API
//          post("/api/addStock", (req, res) -> {
//              String name = req.queryParams("name");
//              int price = Integer.parseInt(req.queryParams("price"));
//              market.addStock(name, price);  // stockRepository 통해 저장
//              return "success";
//          });

//          post("/api/addMoney", (req, res) -> {
//             String playerId = req.queryParams("player");
//             int amount = Integer.parseInt(req.queryParams("amount"));
//             Player player = market.getPlayerById(playerId);
//             if (player != null) {
//                 player.addMoney(amount);
//                 market.savePlayers();
//                 return "success";
//             } else {
//                 res.status(404);
//                 return "Player not found";
//             }
//         });

//      }
 
//      // 8. CORS 허용 설정 (모든 출처에서 접근 가능)
//      private static void cors() {
//          options("/*", (req, res) -> {
//              res.header("Access-Control-Allow-Origin", "*");
//              res.header("Access-Control-Allow-Methods", "GET,POST");
//              res.header("Access-Control-Allow-Headers", "*");
//              return "OK";
//          });
//          before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));
//      }
//  }
 