package controller;
/*
 * <작성자>
 * 진실
 * 
 * <최종 업데이트 날짜>
 * 2025.03.27
 * 
 * <클래스 개요>
 * 스칼라 주식 프로그램의 메인 실행 클래스
 * 
 * - 콘솔 기반 UI로 플레이어 주식 거래를 처리
 * - PlayerRepository 및 StockRepository와 연동
 * 
 * 2. displayPlayerStocks() : 플레이어의 보유 주식 목록 출력
 * 3. displayStockList() : 전체 주식 목록 출력
 * 4. buyStock(Scanner) : 콘솔 입력 기반 주식 구매
 * 5. sellStock(Scanner) : 콘솔 입력 기반 주식 판매
 * 6. getStocks() : 전체 주식 목록 반환
 * 7. getPlayers() : 전체 플레이어 목록 반환
 * 8. buyStock(String, String, int) : 외부 호출용 주식 구매
 * 9. sellStock(String, String, int) : 외부 호출용 주식 판매
 * 10. removePlayer(String) : 플레이어 삭제
 * 11. addStock(String, int) : 종목 추가
 */

 import java.util.List;
 import java.util.Scanner;

import model.Player;
import model.Stock;
import service.PlayerService;
import service.StockService;
import model.PlayerStock;

 
 public class SkalaStockMarket {
     private final StockService stockService;
     private final PlayerService playerService;
     private Player player = null;  // 현재 플레이어
     public SkalaStockMarket() {
        this.stockService = new StockService();
        this.playerService = new PlayerService();
    }
 
     // 2. 플레이어의 보유 주식 목록 출력
     private void displayPlayerStocks() {
         System.out.println("\n######### 플레이어 정보 #########");
         System.out.println("- 플레이어ID : " + player.getplayerId());
         System.out.println("- 보유금액 : " + player.getPlayerMoney());
         System.out.println("- 보유 주식 목록");
         System.out.println(player.getPlayerStocksForMenu());
     }
 
     // 3. 주식 목록 출력
     private void displayStockList() {
         System.out.println("\n=== 주식 목록 ===");
         System.out.println(stockService.getStockListForMenu());
     }
 
     // 4. 콘솔 입력 기반 주식 구매
     private void buyStock(Scanner scanner) {
         System.out.println("\n구매할 주식 번호를 선택하세요:");
         displayStockList();
 
         System.out.print("선택: ");
         int index = scanner.nextInt() - 1;
 
         Stock selectedStock = stockService.findStockByIndex(index);
         if (selectedStock != null) {
             System.out.print("구매할 수량을 입력하세요: ");
             int quantity = scanner.nextInt();
 
             int totalCost = selectedStock.getStockPrice() * quantity;
             int playerMoney = player.getPlayerMoney();
             if (totalCost <= playerMoney) {
                 player.setPlayerMoney(playerMoney - totalCost);
                 player.addStock(new PlayerStock(selectedStock, quantity));
                 System.out.println(quantity + "주를 구매했습니다! 남은 금액: " + player.getPlayerMoney());
                 playerService.savePlayer(); // 변경 저장
             } else {
                 System.out.println("ERROR: 금액이 부족합니다.");
             }
         } else {
             System.out.println("ERROR: 잘못된 선택입니다.");
         }
     }
 
     // 5. 콘솔 입력 기반 주식 판매
     private void sellStock(Scanner scanner) {
         System.out.println("\n판매할 주식 번호를 선택하세요:");
         displayPlayerStocks();
 
         System.out.print("선택: ");
         int index = scanner.nextInt() - 1;
 
         PlayerStock playerStock = player.findStock(index);
         if (playerStock != null) {
             System.out.print("판매할 수량을 입력하세요: ");
             int quantity = scanner.nextInt();
 
             if (quantity > playerStock.getStockQuantity()) {
                 System.out.println("ERROR: 수량이 부족합니다.");
                 return;
             }
 
             Stock baseStock = stockService.findStockByName(playerStock.getStockName());
             int earnedMoney = baseStock.getStockPrice() * quantity;
             player.setPlayerMoney(player.getPlayerMoney() + earnedMoney);
             playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
             player.updatePlayerStock(playerStock);
             playerService.savePlayer(); // 변경 저장
         } else {
             System.out.println("ERROR: 잘못된 선택입니다.");
         }
     }
 
     // 6. 전체 주식 목록 반환 (외부 접근용)
     public List<Stock> getStocks() {
         stockService.loadStock();
         return stockService.getStock();
     }
 
     // 7. 전체 플레이어 목록 반환 (외부 접근용)
     public List<Player> getPlayers() {
         playerService.loadPlayer();
         return playerService.getPlayer();
     }
 
     // 8. 외부 호출용 주식 구매
     public void buyStock(String playerId, String stockName, int quantity) {
         stockService.loadStock();
         playerService.loadPlayer();
 
         Player player = playerService.findPlayerByPlayerId(playerId);
         if (player == null) {
             System.out.println("존재하지 않는 플레이어입니다.");
             return;
         }
 
         Stock stock = stockService.findStockByName(stockName);
         if (stock == null) {
             System.out.println("존재하지 않는 주식입니다.");
             return;
         }
 
         int totalCost = stock.getStockPrice() * quantity;
         if (totalCost <= player.getPlayerMoney()) {
             player.setPlayerMoney(player.getPlayerMoney() - totalCost);
             player.addStock(new PlayerStock(stock, quantity));
             playerService.savePlayer();
         } else {
             System.out.println("ERROR: 금액이 부족합니다.");
         }
     }
 
     // 9. 외부 호출용 주식 판매
     public void sellStock(String playerId, String stockName, int quantity) {
         stockService.loadStock();
         playerService.loadPlayer();
 
         Player player = playerService.findPlayerByPlayerId(playerId);
         if (player == null) {
             System.out.println("존재하지 않는 플레이어입니다.");
             return;
         }
 
         PlayerStock playerStock = player.findStockByName(stockName);
         if (playerStock == null || playerStock.getStockQuantity() < quantity) {
             System.out.println("ERROR: 보유 주식 수량 부족 또는 없음");
             return;
         }
 
         Stock baseStock = stockService.findStockByName(stockName);
         int earnedMoney = baseStock.getStockPrice() * quantity;
         player.setPlayerMoney(player.getPlayerMoney() + earnedMoney);
         playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
         player.updatePlayerStock(playerStock);
         playerService.savePlayer();
     }
 
     // 10. 플레이어 삭제
     public boolean removePlayer(String id) {
         return playerService.removePlayer(id);
     }
 
     // 11. 주식 종목 추가
     public void addStock(String name, int price) {
         stockService.loadStock();
         stockService.addStock(name, price);
     }

     // 특정 플레이어 반환
    public Player getPlayerById(String playerId) {
        playerService.loadPlayer();
        return playerService.findPlayerByPlayerId(playerId);
    }

    // 저장 메서드 노출
    public void savePlayers() {
        playerService.savePlayer();
    }
 }
 