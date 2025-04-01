/*
 * - 작성자 : 진실
 * - 작성일 : 2025.04.01
 * 
 * <클래스 개요>
 * 플레이어 관련 요청 DTO 클래스들
 * 
 * - 클라이언트로부터 받는 데이터 구조 정의
 * - Lombok 어노테이션을 사용하여 boilerplate 코드 최소화
 */

 package com.skala.dto.player;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 
 /**
  * 플레이어 요청 DTO 컨테이너 클래스
  */
 public class PlayerRequestDto {
 
     /**
      * 플레이어 생성 요청 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class CreatePlayer {
         private String id;
     }
 
     /**
      * 플레이어 자금 추가 요청 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class AddMoney {
         private int amount;
     }
 
     /**
      * 주식 구매 요청 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class BuyStock {
         private String stockName;
         private int quantity;
     }
 
     /**
      * 주식 판매 요청 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class SellStock {
         private String stockName;
         private int quantity;
     }
 }