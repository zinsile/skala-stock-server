/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 주식 관련 비즈니스 로직을 처리하는 서비스 클래스
* 
* - JPA 리포지토리를 통해 데이터 접근
* - 파일 기반 동작에서 데이터베이스 기반 동작으로 변경
* - StockServiceInterface 구현
* 
* 1. 주식 관리: 조회, 추가, 업데이트
* 2. 데이터 변환: 메뉴 형식 변환
*/

package com.skala.service;

import com.skala.model.Stock;
import com.skala.repository.StockRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockService implements StockServiceInterface {
   
   private final StockRepository stockRepository;
   
   @Autowired
   public StockService(StockRepository stockRepository) {
       this.stockRepository = stockRepository;
   }

   // 애플리케이션 시작 시 기본 주식 데이터 초기화 (DB가 비어있을 경우)
   @PostConstruct
   public void init() {
       initializeDefaultStocks();
   }

   // 1. 주식 관리: 기본 주식 정보 초기화
   @Override
   @Transactional
   public void initializeDefaultStocks() {
       if (stockRepository.count() == 0) {
           // DB에 주식 정보가 없으면 기본 주식 추가
           stockRepository.save(new Stock("TechCorp", 100));
           stockRepository.save(new Stock("GreenEnergy", 80));
           stockRepository.save(new Stock("HealthPlus", 120));
       }
   }

   // 1. 주식 관리: 전체 주식 목록 반환
   @Override
   public List<Stock> getAllStocks() {
       return stockRepository.findAll();
   }

   // 2. 데이터 변환: 메뉴 표시용 주식 목록 문자열 생성
   @Override
   public String getStockListForMenu() {
       StringBuilder sb = new StringBuilder();
       List<Stock> stocks = getAllStocks();
       
       for (int i = 0; i < stocks.size(); i++) {
           sb.append(i + 1);
           sb.append(". ");
           sb.append(stocks.get(i).toString());
           sb.append(System.lineSeparator());
       }
       return sb.toString();
   }

   // 1. 주식 관리: 이름으로 주식 조회
   @Override
   public Stock findStockByName(String name) {
       return stockRepository.findByStockName(name).orElse(null);
   }

   // 1. 주식 관리: 새 주식 추가
   @Override
   @Transactional
   public Stock addStock(String name, int price) {
       // 이미 존재하는 주식인지 확인
       Optional<Stock> existingStock = stockRepository.findByStockName(name);
       if (existingStock.isPresent()) {
           return null; // 이미 존재하는 주식
       }
       
       Stock newStock = new Stock(name, price);
       return stockRepository.save(newStock);
   }

   // 1. 주식 관리: 주식 가격 업데이트
   @Override
   @Transactional
   public Stock updateStockPrice(String name, int price) {
       Optional<Stock> stockOpt = stockRepository.findByStockName(name);
       if (stockOpt.isPresent()) {
           Stock stock = stockOpt.get();
           stock.setStockPrice(price);
           return stockRepository.save(stock);
       }
       return null;
   }
}