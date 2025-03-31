/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어 서비스의 인터페이스 정의
* 
* - 플레이어 관련 서비스 계층에서 구현해야 할 기능 명세
* - Player 모델의 기능을 서비스 계층으로 이동하여 로직 분리
* 
* 1. 플레이어 관리: 로드, 저장, 조회, 삭제 기능
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
   // 1. 플레이어 관리: 플레이어 데이터 로드
   void loadPlayer();
   
   // 1. 플레이어 관리: 전체 플레이어 목록 반환
   List<Player> getPlayer();
   
   // 1. 플레이어 관리: ID로 플레이어 조회
   Player findPlayerByPlayerId(String id);
   
   // 1. 플레이어 관리: 플레이어 데이터 저장
   void savePlayer();
   
   // 1. 플레이어 관리: 플레이어 삭제
   boolean removePlayer(String id);
   
   // 2. 자금 관리: 플레이어 자금 추가
   void addMoney(Player player, int amount);
   
   // 3. 주식 관리: 플레이어에게 주식 추가
   void addStock(Player player, PlayerStock stock);
   
   // 3. 주식 관리: 플레이어 보유 주식 업데이트
   void updatePlayerStock(Player player, PlayerStock stock);
   
   // 3. 주식 관리: 인덱스로 플레이어 보유 주식 조회
   PlayerStock findStock(Player player, int index);
   
   // 3. 주식 관리: 이름으로 플레이어 보유 주식 조회
   PlayerStock findStockByName(Player player, String stockName);
   
   // 4. 데이터 변환: 파일 저장용 주식 정보 문자열 생성
   String getPlayerStocksForFile(Player player);
   
   // 4. 데이터 변환: 메뉴 표시용 주식 정보 문자열 생성
   String getPlayerStocksForMenu(Player player);
}