/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 주식 관련 비즈니스 로직을 처리하는 서비스 클래스
* 
* - StockRepository를 통해 데이터 접근
* - StockServiceInterface 구현
* 
* 1. 주식 관리: 로드, 조회, 추가 기능
* 2. 데이터 변환: 메뉴 형식 변환
*/

package com.skala.service;

import com.skala.model.Stock;
import com.skala.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService implements StockServiceInterface {
   
   private final StockRepository stockRepository;
   
   @Autowired
   public StockService(StockRepository stockRepository) {
       this.stockRepository = stockRepository;
   }

   // 1. 주식 관리: 주식 데이터 로드
   @Override
   public void loadStock() {
       stockRepository.loadStockList();
   }

   // 1. 주식 관리: 전체 주식 목록 반환
   @Override
   public List<Stock> getStock() {
       return stockRepository.getStockList();
   }

   // 2. 데이터 변환: 메뉴 표시용 주식 목록 문자열 생성
   @Override
   public String getStockListForMenu() {
       return stockRepository.getStockListForMenu();
   }

   // 1. 주식 관리: 인덱스로 주식 조회
   @Override
   public Stock findStockByIndex(int index) {
       return stockRepository.findStock(index);
   }

   // 1. 주식 관리: 이름으로 주식 조회
   @Override
   public Stock findStockByName(String name) {
       return stockRepository.findStock(name);
   }

   // 1. 주식 관리: 새 주식 추가
   @Override
   public void addStock(String name, int price) {
       stockRepository.addStock(name, price);
   }
}