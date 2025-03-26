class Stock {
    String stockName;
    int stockPrice;

    public Stock() {
        
    }

    public Stock(String name, int price) {
        this.stockName = name;
        this.stockPrice = price;
    }

    public String getStockName() {
        return stockName;
    }

    public int getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(int price) {
        this.stockPrice = price;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stockName);
        sb.append(":");
        sb.append(stockPrice);
        return sb.toString();
    }
}
