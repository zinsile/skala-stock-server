/*
 * - 작성자 : 진실
 * - 작성일 : 2025.04.01
 * 
 * <클래스 개요>
 * 플레이어 관련 응답 DTO 클래스들
 * 
 * - 클라이언트에게 반환하는 데이터 구조 정의
 * - Lombok 어노테이션을 사용하여 boilerplate 코드 최소화
 * - Entity -> DTO 변환 메소드 포함
 */

 package com.skala.dto.player;

 import com.skala.model.Player;
 import com.skala.model.PlayerStock;
 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 
 import java.util.List;
 import java.util.stream.Collectors;
 
 /**
  * 플레이어 정보 응답 DTO
  */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 public class PlayerResponseDto {
     private String playerId;
     private int playerMoney;
     private List<PlayerStockInfo> playerStocks;
 
     /**
      * Entity -> DTO 변환 메소드
      */
     public static PlayerResponseDto from(Player player) {
         List<PlayerStockInfo> stockInfos = player.getPlayerStocks().stream()
                 .map(PlayerStockInfo::from)
                 .collect(Collectors.toList());
 
         return PlayerResponseDto.builder()
                 .playerId(player.getPlayerId())
                 .playerMoney(player.getPlayerMoney())
                 .playerStocks(stockInfos)
                 .build();
     }
 
     /**
      * 플레이어 주식 정보 응답 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class PlayerStockInfo {
         private String stockName;
         private int stockPrice;
         private int stockQuantity;
 
         /**
          * Entity -> DTO 변환 메소드
          */
         public static PlayerStockInfo from(PlayerStock playerStock) {
             return PlayerStockInfo.builder()
                     .stockName(playerStock.getStockName())
                     .stockPrice(playerStock.getStockPrice())
                     .stockQuantity(playerStock.getStockQuantity())
                     .build();
         }
     }
 
     /**
      * 거래 결과 응답 DTO
      */
     @Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     public static class TransactionResult {
         private String message;
         private boolean success;
         private int currentMoney;
     }
 }