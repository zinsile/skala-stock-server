/*
 * - 작성자 : 진실
 * - 작성일 : 2025.03.30
 * - 수정일 : 2025.03.31
 * 
 * <클래스 개요>
 * 플레이어가 보유한 주식 정보를 관리하는 클래스
 * 
 * - Stock 클래스를 상속받아 주식의 기본 정보(이름, 가격)에 수량 정보를 추가
 * - Lombok 어노테이션을 사용하여 getter, setter 및 기본 생성자 자동 생성
 * - 기존 Stock 객체와 수량을 받아 PlayerStock을 생성하는 생성자 제공
 * - 문자열 형태로 주식 정보를 받아 PlayerStock을 생성하는 생성자 제공
 * - toString() 메소드를 오버라이드하여 상위 클래스의 정보와 수량을 포함한 문자열 반환
*/

package com.skala.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerStock extends Stock {
   private int stockQuantity;    // 주식 보유 수량

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

   // 상위 클래스의 toString()에 수량 정보를 추가하여 반환
   @Override
   public String toString() {
       return super.toString() + ":" + this.stockQuantity;
   }
}