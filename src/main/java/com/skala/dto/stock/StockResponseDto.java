/*
 * - 작성자 : 진실
 * - 작성일 : 2025.04.01
 * 
 * <클래스 개요>
 * 주식 관련 응답 DTO 클래스들
 * 
 * - 클라이언트에게 반환하는 데이터 구조 정의
 * - Lombok 어노테이션을 사용하여 boilerplate 코드 최소화
 * - Entity -> DTO 변환 메소드 포함
 */

 package com.skala.dto.stock;

 import com.skala.model.Stock;
 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 
 import java.util.List;
 import java.util.stream.Collectors;
 
 /**
  * 주식 정보 응답 DTO
  */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 public class StockResponseDto {
     private String stockName;
     private int stockPrice;
 
     /**
      * Entity -> DTO 변환 메소드
      */
     public static StockResponseDto from(Stock stock) {
         return StockResponseDto.builder()
                 .stockName(stock.getStockName())
                 .stockPrice(stock.getStockPrice())
                 .build();
     }
 
     /**
      * 주식 목록 응답 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class StockList {
         private List<StockResponseDto> stocks;
 
         /**
          * Entity 목록 -> DTO 변환 메소드
          */
         public static StockList from(List<Stock> stocks) {
             List<StockResponseDto> stockInfos = stocks.stream()
                     .map(StockResponseDto::from)
                     .collect(Collectors.toList());
 
             return StockList.builder()
                     .stocks(stockInfos)
                     .build();
         }
     }
 }