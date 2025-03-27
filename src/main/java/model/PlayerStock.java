package model;
/*
 * <작성자>
 * 진실
 * 
 * <최종 업데이트 날짜>
 * 2025.03.27
 * 
 * <클래스 개요>
 * 플레이어가 보유한 주식 정보를 저장하는 클래스 (Stock 클래스 상속)
 * 
 * - Stock 클래스의 이름, 가격 정보를 상속받고
 * - 보유 수량(stockQuantity)을 추가로 관리
 * 
 * 1. PlayerStock(Stock stock, int quantity) : 주식 객체와 수량으로 생성
 * 2. PlayerStock(String name, String price, String quantity) : 문자열 정보로 생성 (파일 로드용)
 * 3. getStockQuantity() : 주식 수량 반환
 * 4. setStockQuantity(int count) : 주식 수량 설정
 * 5. toString() : 파일 저장 및 출력용 문자열 반환 (name:price:quantity)
 */

// 클래스 상속

class PlayerStock extends Stock {
    private int stockQuantity;  // 보유 주식 수량

    // 1. 주식 객체와 수량으로 생성자 구성 (Stock 상속 고려)
    public PlayerStock(Stock stock, int quantity) {
        this.stockName = stock.getStockName();
        this.stockPrice = stock.getStockPrice();
        this.stockQuantity = quantity;
    }

    // 2. 파일에서 읽은 문자열 정보로 객체 생성
    public PlayerStock(String name, String price, String quantity) {
        this.stockName = name;
        this.stockPrice = Integer.parseInt(price);
        this.stockQuantity = Integer.parseInt(quantity);
    }

    // 3. 보유 수량 반환
    public int getStockQuantity() {
        return this.stockQuantity;
    }

    // 4. 보유 수량 설정
    public void setStockQuantity(int count) {
        this.stockQuantity = count;
    }

    // 5. 주식 정보 문자열 반환 (name:price:quantity 형식)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // name:price
        sb.append(":");
        sb.append(this.stockQuantity);
        return sb.toString();
    }
}
