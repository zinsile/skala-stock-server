/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 주식 정보를 파일 시스템에 저장하고 관리하는 리포지토리 클래스
* 
* - 텍스트 파일 형태로 주식 데이터 관리
* - 파일이 없을 경우 기본 주식 데이터 생성
* 
* 1. 파일 입출력: 주식 정보 로드 및 저장
* 2. 데이터 변환: 문자열과 주식 객체 간 변환
* 3. 주식 관리: 조회, 추가, 메뉴 형식 변환
*/

package com.skala.repository;

import com.skala.model.Stock;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockRepository {
   private final String STOCK_FILE = "data/stocks.txt";  // 주식 정보 저장 파일 경로
   private ArrayList<Stock> stockList = new ArrayList<>();  // 메모리 상의 주식 목록

   // 1. 파일 입출력: 파일에서 주식 목록 로드
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

   // 1. 파일 입출력: 주식 목록을 파일에 저장
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

   // 2. 데이터 변환: 텍스트 라인을 Stock 객체로 변환
   private Stock parseLineToStock(String line) {
       String[] fields = line.split(",");
       if (fields.length > 1) {
           return new Stock(fields[0], Integer.parseInt(fields[1]));
       } else {
           System.out.println("파일 라인을 분석할 수 없습니다. line=" + line);
           return null;
       }
   }

   // 3. 주식 관리: 메뉴 표시용 주식 목록 문자열 생성
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

   // 3. 주식 관리: 전체 주식 목록 반환
   public List<Stock> getStockList() {
       return this.stockList;
   }

   // 3. 주식 관리: 인덱스로 주식 조회
   public Stock findStock(int index) {
       if (index >= 0 && index < stockList.size()) {
           return stockList.get(index);
       }
       return null;
   }

   // 3. 주식 관리: 이름으로 주식 조회
   public Stock findStock(String name) {
       for (Stock stock : stockList) {
           if (stock.getStockName().equals(name)) {
               return stock;
           }
       }
       return null;
   }

   // 3. 주식 관리: 새 주식 추가
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