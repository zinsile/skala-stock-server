/*
 * - 작성자 : 진실
 * - 작성일 : 2025.04.01
 * 
 * <클래스 개요>
 * 주식 관련 요청 DTO 클래스들
 * 
 * - 클라이언트로부터 받는 데이터 구조 정의
 * - Lombok 어노테이션을 사용하여 boilerplate 코드 최소화
 */

 package com.skala.dto.stock;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 
 /**
  * 주식 요청 DTO 컨테이너 클래스
  */
 public class StockRequestDto {
 
     /**
      * 주식 추가 요청 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class CreateStock {
         private String name;
         private int price;
     }
 
     /**
      * 주식 가격 업데이트 요청 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class UpdatePrice {
         private int price;
     }
 }