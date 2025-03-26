import java.util.List;
import java.util.Scanner;

public class SkalaStockMarket {
    private PlayerRepository playerRepository = new PlayerRepository();
    private StockRepository stockRepository = new StockRepository();
    private Player player = null;

    public void start() {

        // 주식 레파지토리에서 주식 정보를 불러옴
        stockRepository.loadStockList();

        // 플레이어 레파지토리에서 플레이어 정보를 불러옴
        playerRepository.loadPlayerList();

        // 콘솔로 입력을 받을 수 있도록 스캐너 설정
        Scanner scanner = new Scanner(System.in);

        System.out.print("플레이어 ID를 입력하세요: ");
        String playerId = scanner.nextLine();
        player = playerRepository.findPlayer(playerId);
        if (player == null) { // 새로운 플레이어
            player = new Player(playerId);

            System.out.print("초기 투자금을 입력하세요: ");
            int money = scanner.nextInt();
            player.setPlayerMoney(money);
            playerRepository.addPlayer(player);
        }
        displayPlayerStocks();

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

    // 플레이어의 보유 주식 목록 표시
    private void displayPlayerStocks() {
        System.out.println("\n######### 플레이어 정보 #########");
        System.out.println("- 플레이어ID : " + player.getplayerId());
        System.out.println("- 보유금액 : " + player.getPlayerMoney());
        System.out.println("- 보유 주식 목록");
        System.out.println(player.getPlayerStocksForMenu());
    }

    // 주식 목록 표시
    private void displayStockList() {
        System.out.println("\n=== 주식 목록 ===");
        System.out.println(stockRepository.getStockListForMenu());
    }

    // 주식 구매
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

                // 변경된 내용을 파일로 저장
                playerRepository.savePlayerList();
            } else {
                System.out.println("ERROR: 금액이 부족합니다.");
            }
        } else {
            System.out.println("ERROR: 잘못된 선택입니다.");
        }
    }

    // 주식 판매
    private void sellStock(Scanner scanner) {
        System.out.println("\n판매할 주식 번호를 선택하세요:");
        displayPlayerStocks();

        System.out.print("선택: ");
        int index = scanner.nextInt() - 1;

        PlayerStock playerStock = player.findStock(index);
        if (playerStock != null) {
            System.out.print("판매할 수량을 입력하세요: ");
            int quantity = scanner.nextInt();

            // 어얼리 리턴
            if (quantity > playerStock.getStockQuantity()) {
                System.out.println("ERROR: 수량이 부족합니다.");
                return;
            }

            Stock baseStock = stockRepository.findStock(playerStock.getStockName());
            int playerMoney = player.getPlayerMoney() + baseStock.getStockPrice() * quantity;
            player.setPlayerMoney(playerMoney);

            playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
            player.updatePlayerStock(playerStock);

            // 변경된 내용을 파일로 저장
            playerRepository.savePlayerList();

        } else {
            System.out.println("ERROR: 잘못된 선택입니다.");
        }
    }

    public List<Stock> getStocks() {
        stockRepository.loadStockList(); // 항상 최신으로
        return stockRepository.getStockList();
    }

    public List<Player> getPlayers() {
        playerRepository.loadPlayerList();
        return playerRepository.getPlayerList();
    }
    
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

    public boolean removePlayer(String id) {
        return playerRepository.removePlayer(id);
    }

    // 종목 추가 기능 메서드 위임 추가
    public void addStock(String name, int price) {
        stockRepository.loadStockList(); // 기존 목록 불러오기
        stockRepository.addStock(name, price); // 종목 추가 + 저장
    }
    
}
