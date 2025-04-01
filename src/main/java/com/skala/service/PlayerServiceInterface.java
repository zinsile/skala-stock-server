/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어 서비스의 인터페이스 정의
* 
* - JPA 사용을 위한 메소드 수정
* - Player 모델의 기능을 서비스 계층으로 이동하여 로직 분리
* 
* 1. 플레이어 관리: 조회, 생성, 삭제 기능
* 2. 자금 관리: 플레이어 자금 추가
* 3. 주식 관리: 주식 추가, 업데이트, 조회
* 4. 데이터 변환: 주식 정보를 다양한 형식으로 변환
*/

package com.skala.service;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import com.skala.model.Stock;

import java.util.List;

public interface PlayerServiceInterface {
   // 1. 플레이어 관리: 전체 플레이어 목록 반환
   List<Player> getAllPlayers();
   
   // 1. 플레이어 관리: ID로 플레이어 조회
   Player findPlayerByPlayerId(String id);
   
   // 1. 플레이어 관리: 새 플레이어 생성
   Player createPlayer(String id);
   
   // 1. 플레이어 관리: 플레이어 저장
   Player savePlayer(Player player);
   
   // 1. 플레이어 관리: 플레이어 삭제
   boolean removePlayer(String id);
   
   // 2. 자금 관리: 플레이어 자금 추가
   void addMoney(Player player, int amount);
   
   // 3. 주식 관리: 플레이어에게 주식 추가
   PlayerStock addStock(Player player, Stock stock, int quantity);
   
   // 3. 주식 관리: 플레이어 보유 주식 업데이트
   void updatePlayerStock(Player player, PlayerStock stock);
   
   // 3. 주식 관리: 이름으로 플레이어 보유 주식 조회
   PlayerStock findStockByName(Player player, String stockName);
   
   // 4. 데이터 변환: 메뉴 표시용 주식 정보 문자열 생성
   String getPlayerStocksForMenu(Player player);
}