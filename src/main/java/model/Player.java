package model;

import java.util.ArrayList;

public class Player {

    private String playerId; // 플레이어 ID
    private int playerMoney; // 플레이어 자금
    private ArrayList<PlayerStock> playerStocks = new ArrayList<>(); // 보유 주식 목록

    // 기본 생성자
    public Player() {
    }

    // 플레이어 ID를 받아 초기 자금으로 생성
    public Player(String id) {
        this.playerId = id;
        this.playerMoney = 10000;
    }

    // 플레이어 ID 설정
    public void setplayerId(String id) {
        this.playerId = id;
    }

    // 플레이어 ID 반환
    public String getplayerId() {
        return this.playerId;
    }

    // 플레이어 자금 반환
    public int getPlayerMoney() {
        return this.playerMoney;
    }

    // 플레이어 자금 설정
    public void setPlayerMoney(int money) {
        this.playerMoney = money;
    }

    // 투자금 추가 메서드
    public void addMoney(int amount) {
        if (amount > 0) {
            this.playerMoney += amount;
        }
    }

    // 보유 주식 리스트 반환
    public ArrayList<PlayerStock> getPlayerStocks() {
        return this.playerStocks;
    }

    // 보유 주식 리스트 설정
    public void setPlayerStocks(ArrayList<PlayerStock> stocks) {
        this.playerStocks = stocks;
    }

    // 1. 주식을 보유 목록에 추가 (중복 시 수량만 증가)
    public void addStock(PlayerStock stock) {
        boolean stockExists = false;

        for (PlayerStock existingStock : playerStocks) {
            if (existingStock.getStockName().equals(stock.getStockName())) {
                existingStock.setStockPrice(stock.getStockPrice());
                existingStock.setStockQuantity(existingStock.getStockQuantity() + stock.getStockQuantity());
                stockExists = true;
                break;
            }
        }

        if (!stockExists) {
            playerStocks.add(stock);
        }
    }

    // 2. 보유 주식 정보 갱신 (수량이 0이면 제거)
    public void updatePlayerStock(PlayerStock stock) {
        for (PlayerStock existingStock : playerStocks) {
            if (existingStock.getStockName().equals(stock.getStockName())) {
                existingStock.setStockPrice(stock.getStockPrice());
                existingStock.setStockQuantity(existingStock.getStockQuantity());
                if (existingStock.getStockQuantity() == 0) {
                    playerStocks.remove(existingStock);
                }
                break;
            }
        }
    }

    // 3. 인덱스로 주식 검색
    public PlayerStock findStock(int index) {
        if (index >= 0 && index < playerStocks.size()) {
            return playerStocks.get(index);
        }
        return null;
    }

    // 5. 파일 저장용 주식 문자열 반환 (예: name:price:qty|...)
    public String getPlayerStocksForFile() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playerStocks.size(); i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(playerStocks.get(i));
        }
        return sb.toString();
    }

    // 6. 메뉴 출력용 주식 문자열 반환 (예: 1. name:price:qty ...)
    public String getPlayerStocksForMenu() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playerStocks.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(playerStocks.get(i).toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    // 4. 이름으로 주식 검색
    public PlayerStock findStockByName(String stockName) {
        for (PlayerStock ps : this.playerStocks) {
            if (ps.getStockName().equals(stockName)) {
                return ps;
            }
        }
        return null;
    }
}
