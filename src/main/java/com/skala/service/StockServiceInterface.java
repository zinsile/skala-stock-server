/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 주식 서비스의 인터페이스 정의
* 
* - 주식 관련 서비스 계층에서 구현해야 할 기능 명세
* 
* 1. 주식 관리: 로드, 조회, 추가 기능
* 2. 데이터 변환: 메뉴 형식 변환
*/

package com.skala.service;

import com.skala.model.Stock;
import java.util.List;

public interface StockServiceInterface {
   // 1. 주식 관리: 주식 데이터 로드
   void loadStock();
   
   // 1. 주식 관리: 전체 주식 목록 반환
   List<Stock> getStock();
   
   // 2. 데이터 변환: 메뉴 표시용 주식 목록 문자열 생성
   String getStockListForMenu();
   
   // 1. 주식 관리: 인덱스로 주식 조회
   Stock findStockByIndex(int index);
   
   // 1. 주식 관리: 이름으로 주식 조회
   Stock findStockByName(String name);
   
   // 1. 주식 관리: 새 주식 추가
   void addStock(String name, int price);
}