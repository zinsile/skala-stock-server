/*
 * - 작성자 : 진실
 * - 작성일 : 2025.03.30
 * - 수정일 : 2025.03.31
 * 
 * <클래스 개요>
 * 플레이어가 보유한 주식 정보를 관리하는 엔티티 클래스
 * 
 * - 별도의 엔티티로 분리하여 플레이어와 주식간의 관계 표현
 * - JPA 엔티티 관계 설정 추가
 * - Lombok 어노테이션을 사용하여 getter, setter 및 기본 생성자 자동 생성
 * - 복합키를 사용하여 플레이어별 보유 주식 구분 (playerId + stockName)
 * - toString() 메소드를 오버라이드하여 주식 정보와 수량을 포함한 문자열 반환
*/

package com.skala.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "player_stocks")
@Getter
@Setter
@NoArgsConstructor
public class PlayerStock {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "player_id")
   @JsonBackReference
   private Player player;
   
   private String stockName;      // 주식 이름
   private int stockPrice;        // 주식 가격 (구매 당시)
   private int stockQuantity;     // 주식 보유 수량

   // Stock 객체와 수량을 받아 PlayerStock 생성
   public PlayerStock(Stock stock, int quantity) {
       this.stockName = stock.getStockName();
       this.stockPrice = stock.getStockPrice();
       this.stockQuantity = quantity;
   }

   // 문자열 형태의 이름, 가격, 수량으로 PlayerStock 생성
   public PlayerStock(String name, String price, String quantity) {
       this.stockName = name;
       this.stockPrice = Integer.parseInt(price);
       this.stockQuantity = Integer.parseInt(quantity);
   }

   // 주식 정보와 수량을 포함한 문자열 반환
   @Override
   public String toString() {
       return stockName + ":" + stockPrice + ":" + this.stockQuantity;
   }
}