package com.skala.repository;

import com.skala.model.Stock;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockRepository {
    private final String STOCK_FILE = "data/stocks.txt";
    private ArrayList<Stock> stockList = new ArrayList<>();

    public void loadStockList() {
        stockList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Stock stock = parseLineToStock(line);
                if (stock != null) {
                    stockList.add(stock);
                }
            }
        } catch (IOException e) {
            System.out.println("파일이 없거나 파일을 불러오는 중 오류가 발생했습니다. 주식 정보를 초기화 합니다.");
            // 파일이 없을 경우 기본 주식 데이터 추가
            stockList.add(new Stock("TechCorp", 100));
            stockList.add(new Stock("GreenEnergy", 80));
            stockList.add(new Stock("HealthPlus", 120));
        }
    }

    public void saveStockList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_FILE))) {
            for (Stock stock : stockList) {
                writer.write(stock.getStockName() + "," + stock.getStockPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일에 저장하는 중 오류가 발생했습니다.");
        }
    }

    private Stock parseLineToStock(String line) {
        String[] fields = line.split(",");
        if (fields.length > 1) {
            return new Stock(fields[0], Integer.parseInt(fields[1]));
        } else {
            System.out.println("파일 라인을 분석할 수 없습니다. line=" + line);
            return null;
        }
    }

    public String getStockListForMenu() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stockList.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(stockList.get(i).toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public List<Stock> getStockList() {
        return this.stockList;
    }

    public Stock findStock(int index) {
        if (index >= 0 && index < stockList.size()) {
            return stockList.get(index);
        }
        return null;
    }

    public Stock findStock(String name) {
        for (Stock stock : stockList) {
            if (stock.getStockName().equals(name)) {
                return stock;
            }
        }
        return null;
    }

    public void addStock(String name, int price) {
        Stock existing = findStock(name);
        if (existing == null) {
            stockList.add(new Stock(name, price));
            saveStockList();
        } else {
            System.out.println("이미 존재하는 주식입니다.");
        }
    }
}