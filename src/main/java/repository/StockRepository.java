package repository;
/*
 * <작성자>
 * 진실
 * 
 * <최종 업데이트 날짜>
 * 2025.03.27
 * 
 * <클래스 개요>
 * 주식(Stock) 정보를 관리하는 저장소 클래스
 * 
 * - 주식 정보를 파일로부터 불러오거나 저장
 * - 주식 추가, 검색 기능 제공
 * 
 * 1. loadStockList() : 파일에서 주식 목록 로드
 * 2. saveStockList() : 주식 목록을 파일로 저장
 * 3. parseLineToStock(String line) : 파일 한 줄을 Stock 객체로 변환
 * 4. getStockListForMenu() : 주식 목록을 메뉴 출력용 문자열로 반환
 * 5. getStockList() : 주식 목록 반환 (List 형태)
 * 6. findStock(int index) : 인덱스로 주식 검색
 * 7. findStock(String name) : 이름으로 주식 검색
 * 8. addStock(String name, int price) : 새 주식 종목 추가
 */

 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;

 import model.Stock;
 
 public class StockRepository {
     // 주식 정보를 저장할 파일 (형식: "주식명,주가")
     private final String STOCK_FILE = "data/stocks.txt";
 
     // 주식 정보 목록 (메모리)
     private ArrayList<Stock> stockList = new ArrayList<>();
 
     // 1. 주식 정보를 파일에서 읽어옴
     public void loadStockList() {
         stockList.clear(); // 🔥 중복 방지를 위해 초기화
 
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
 
     // 2. 주식 목록을 파일에 저장
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
 
     // 3. 파일 한 줄을 Stock 객체로 변환
     private Stock parseLineToStock(String line) {
         String fileds[] = line.split(",");
         if (fileds.length > 1) {
             return new Stock(fileds[0], Integer.parseInt(fileds[1]));
         } else {
             System.out.println("파일 라인을 분석할 수 없습니다. line=" + line);
             return null;
         }
     }
 
     // 4. 주식 목록을 메뉴 출력용 문자열로 반환
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
 
     // 5. 주식 목록 반환
     public List<Stock> getStockList() {
         return this.stockList;
     }
 
     // 6. 인덱스로 주식 검색 (오버로딩)
     public Stock findStock(int index) {
         if (index >= 0 && index < stockList.size()) {
             return stockList.get(index);
         }
         return null;
     }
 
     // 7. 이름으로 주식 검색 (오버로딩)
     public Stock findStock(String name) {
         for (Stock stock : stockList) {
             if (stock.getStockName().equals(name)) {
                 return stock;
             }
         }
         return null;
     }
 
     // 8. 주식 종목 추가 (중복 방지 포함)
     public void addStock(String name, int price) {
         Stock existing = findStock(name);
         if (existing == null) {
             stockList.add(new Stock(name, price));
             saveStockList(); // 파일에도 저장
         } else {
             System.out.println("이미 존재하는 주식입니다.");
         }
     }
 }
 