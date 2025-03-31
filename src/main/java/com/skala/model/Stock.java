/*
 * - 작성자 : 진실
 * - 작성일 : 2025.03.30
 * - 수정일 : 2025.03.31
 * 
 * <클래스 개요>
 * 주식의 기본 정보를 관리하는 클래스
 * 
 * - 주식의 이름과 가격 정보를 저장
 * - Lombok 어노테이션을 사용하여 getter, setter, 생성자, equals, hashCode 등 자동 생성
 * 
 * 1. 생성: 기본 생성자 또는 모든 필드를 받는 생성자로 주식 정보 초기화
 * 2. 정보 접근: @Data 어노테이션으로 getter/setter 메소드 자동 생성
 * 3. 문자열 변환: toString()으로 주식 정보를 문자열로 변환
*/

package com.skala.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                // 2. 정보 접근: getter/setter 및 기타 유틸리티 메소드 자동 생성
@NoArgsConstructor   // 1. 생성: 매개변수 없는 기본 생성자 제공
@AllArgsConstructor  // 1. 생성: 모든 필드를 매개변수로 받는 생성자 제공
public class Stock {
   protected String stockName;    // 주식 이름
   protected int stockPrice;      // 주식 가격

   // 3. 문자열 변환: 주식 이름과 가격을 콜론으로 구분하여 문자열로 반환
   @Override
   public String toString() {
       return stockName + ":" + stockPrice;
   }
}