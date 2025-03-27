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
 * 1. start() : 프로그램 시작 및 메인 메뉴 루프
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
 
 public class SkalaStockMarket {
     private PlayerRepository playerRepository = new PlayerRepository();  // 플레이어 저장소
     private StockRepository stockRepository = new StockRepository();     // 주식 저장소
     private Player player = null;  // 현재 플레이어
 
     // 1. 프로그램 시작 및 메인 메뉴 루프
     public void start() {
         // 주식 및 플레이어 정보 로드
         stockRepository.loadStockList();
         playerRepository.loadPlayerList();
 
         Scanner scanner = new Scanner(System.in);
 
         System.out.print("플레이어 ID를 입력하세요: ");
         String playerId = scanner.nextLine();
         player = playerRepository.findPlayer(playerId);
 
         // 신규 플레이어 등록
         if (player == null) {
             player = new Player(playerId);
             System.out.print("초기 투자금을 입력하세요: ");
             int money = scanner.nextInt();
             player.setPlayerMoney(money);
             playerRepository.addPlayer(player);
         }
 
         displayPlayerStocks(); // 초기 보유 주식 출력
 
         // 프로그램 루프
         boolean running = true;
         while (running) {
             System.out.println("\n=== 스칼라 주식 프로그램 메뉴 ===");
             System.out.println("1. 보유 주식 목록");
             System.out.println("2. 주식 구매");
             System.out.println("3. 주식 판매");
             System.out.println("0. 프로그램 종료");
 
             System.out.print("선택: ");
             int code = scanner.nextInt();
 
             switch (code) {
                 case 1:
                     displayPlayerStocks();
                     break;
                 case 2:
                     buyStock(scanner);
                     break;
                 case 3:
                     sellStock(scanner);
                     break;
                 case 4:
                     System.out.print("추가할 금액 입력: ");
                     int addAmount = scanner.nextInt();
                     if (addAmount > 0) {
                         player.addMoney(addAmount);
                         System.out.println("현재 자금: " + player.getPlayerMoney());
                         playerRepository.savePlayerList(); // 저장
                     } else {
                         System.out.println("0보다 큰 금액을 입력하세요.");
                     }
                     break;
                 case 0:
                     System.out.println("프로그램을 종료합니다...Bye");
                     running = false;
                     break;
                 default:
                     System.out.println("올바른 번호를 선택하세요.");
             }
         }
 
         scanner.close();
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
         System.out.println(stockRepository.getStockListForMenu());
     }
 
     // 4. 콘솔 입력 기반 주식 구매
     private void buyStock(Scanner scanner) {
         System.out.println("\n구매할 주식 번호를 선택하세요:");
         displayStockList();
 
         System.out.print("선택: ");
         int index = scanner.nextInt() - 1;
 
         Stock selectedStock = stockRepository.findStock(index);
         if (selectedStock != null) {
             System.out.print("구매할 수량을 입력하세요: ");
             int quantity = scanner.nextInt();
 
             int totalCost = selectedStock.getStockPrice() * quantity;
             int playerMoney = player.getPlayerMoney();
             if (totalCost <= playerMoney) {
                 player.setPlayerMoney(playerMoney - totalCost);
                 player.addStock(new PlayerStock(selectedStock, quantity));
                 System.out.println(quantity + "주를 구매했습니다! 남은 금액: " + player.getPlayerMoney());
                 playerRepository.savePlayerList(); // 변경 저장
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
 
             Stock baseStock = stockRepository.findStock(playerStock.getStockName());
             int earnedMoney = baseStock.getStockPrice() * quantity;
             player.setPlayerMoney(player.getPlayerMoney() + earnedMoney);
             playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
             player.updatePlayerStock(playerStock);
             playerRepository.savePlayerList(); // 변경 저장
         } else {
             System.out.println("ERROR: 잘못된 선택입니다.");
         }
     }
 
     // 6. 전체 주식 목록 반환 (외부 접근용)
     public List<Stock> getStocks() {
         stockRepository.loadStockList();
         return stockRepository.getStockList();
     }
 
     // 7. 전체 플레이어 목록 반환 (외부 접근용)
     public List<Player> getPlayers() {
         playerRepository.loadPlayerList();
         return playerRepository.getPlayerList();
     }
 
     // 8. 외부 호출용 주식 구매
     public void buyStock(String playerId, String stockName, int quantity) {
         stockRepository.loadStockList();
         playerRepository.loadPlayerList();
 
         Player player = playerRepository.findPlayer(playerId);
         if (player == null) {
             System.out.println("존재하지 않는 플레이어입니다.");
             return;
         }
 
         Stock stock = stockRepository.findStock(stockName);
         if (stock == null) {
             System.out.println("존재하지 않는 주식입니다.");
             return;
         }
 
         int totalCost = stock.getStockPrice() * quantity;
         if (totalCost <= player.getPlayerMoney()) {
             player.setPlayerMoney(player.getPlayerMoney() - totalCost);
             player.addStock(new PlayerStock(stock, quantity));
             playerRepository.savePlayerList();
         } else {
             System.out.println("ERROR: 금액이 부족합니다.");
         }
     }
 
     // 9. 외부 호출용 주식 판매
     public void sellStock(String playerId, String stockName, int quantity) {
         stockRepository.loadStockList();
         playerRepository.loadPlayerList();
 
         Player player = playerRepository.findPlayer(playerId);
         if (player == null) {
             System.out.println("존재하지 않는 플레이어입니다.");
             return;
         }
 
         PlayerStock playerStock = player.findStockByName(stockName);
         if (playerStock == null || playerStock.getStockQuantity() < quantity) {
             System.out.println("ERROR: 보유 주식 수량 부족 또는 없음");
             return;
         }
 
         Stock baseStock = stockRepository.findStock(stockName);
         int earnedMoney = baseStock.getStockPrice() * quantity;
         player.setPlayerMoney(player.getPlayerMoney() + earnedMoney);
         playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
         player.updatePlayerStock(playerStock);
         playerRepository.savePlayerList();
     }
 
     // 10. 플레이어 삭제
     public boolean removePlayer(String id) {
         return playerRepository.removePlayer(id);
     }
 
     // 11. 주식 종목 추가
     public void addStock(String name, int price) {
         stockRepository.loadStockList();
         stockRepository.addStock(name, price);
     }

     // 특정 플레이어 반환
    public Player getPlayerById(String playerId) {
        playerRepository.loadPlayerList();
        return playerRepository.findPlayer(playerId);
    }

    // 저장 메서드 노출
    public void savePlayers() {
        playerRepository.savePlayerList();
    }
 }
 