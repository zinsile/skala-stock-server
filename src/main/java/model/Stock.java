package model;
/*
 * <작성자>
 * 진실
 * 
 * <최종 업데이트 날짜>
 * 2025.03.27
 * 
 * <클래스 개요>
 * 주식(Stock)의 기본 정보를 담는 클래스
 * 
 * - 주식 이름(stockName)과 현재 가격(stockPrice)을 저장
 * - PlayerStock에서 상속받아 사용됨
 * 
 * 1. Stock() : 기본 생성자
 * 2. Stock(String, int) : 주식 이름과 가격으로 초기화하는 생성자
 * 3. getStockName() : 주식 이름 반환
 * 4. getStockPrice() : 주식 가격 반환
 * 5. setStockPrice(int) : 주식 가격 설정
 * 6. toString() : 주식 정보를 문자열로 반환 (name:price 형식)
 */

 class Stock {
    String stockName;   // 주식 이름
    int stockPrice;     // 주식 가격

    // 1. 기본 생성자
    public Stock() {}

    // 2. 주식 이름과 가격으로 초기화하는 생성자
    public Stock(String name, int price) {
        this.stockName = name;
        this.stockPrice = price;
    }

    // 3. 주식 이름 반환
    public String getStockName() {
        return stockName;
    }

    // 4. 주식 가격 반환
    public int getStockPrice() {
        return stockPrice;
    }

    // 5. 주식 가격 설정
    public void setStockPrice(int price) {
        this.stockPrice = price;
    }

    // 6. 주식 정보를 문자열로 반환 (예: name:price)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stockName);
        sb.append(":");
        sb.append(stockPrice);
        return sb.toString();
    }
}
