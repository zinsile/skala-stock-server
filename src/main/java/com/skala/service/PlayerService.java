/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어 관련 비즈니스 로직을 처리하는 서비스 클래스
* 
* - PlayerRepository를 통해 데이터 접근
* - PlayerServiceInterface 구현
* 
* 1. 플레이어 관리: 로드, 저장, 조회, 삭제
* 2. 자금 관리: 플레이어 자금 추가
* 3. 주식 관리: 추가, 업데이트, 조회
* 4. 데이터 변환: 주식 정보를 다양한 형식으로 변환
*/

package com.skala.service;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import com.skala.model.Stock;
import com.skala.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService implements PlayerServiceInterface {
   
   private final PlayerRepository playerRepository;
   
   @Autowired
   public PlayerService(PlayerRepository playerRepository) {
       this.playerRepository = playerRepository;
   }

   // 1. 플레이어 관리: 플레이어 데이터 로드
   @Override
   public void loadPlayer() {
       playerRepository.loadPlayerList();
   }

   // 1. 플레이어 관리: 전체 플레이어 목록 반환
   @Override
   public List<Player> getPlayer() {
       return playerRepository.getPlayerList();
   }

   // 1. 플레이어 관리: ID로 플레이어 조회
   @Override
   public Player findPlayerByPlayerId(String id) {
       return playerRepository.findPlayer(id);
   }

   // 1. 플레이어 관리: 플레이어 데이터 저장
   @Override
   public void savePlayer() {
       playerRepository.savePlayerList();
   }

   // 1. 플레이어 관리: 플레이어 삭제
   @Override
   public boolean removePlayer(String id) {
       return playerRepository.removePlayer(id);
   }
   
   // 2. 자금 관리: 플레이어 자금 추가
   @Override
   public void addMoney(Player player, int amount) {
       if (amount > 0) {
           player.setPlayerMoney(player.getPlayerMoney() + amount);
       }
   }
   
   // 3. 주식 관리: 플레이어에게 주식 추가 (중복 시 수량만 증가)
   @Override
   public void addStock(Player player, PlayerStock stock) {
       boolean stockExists = false;

       for (PlayerStock existingStock : player.getPlayerStocks()) {
           if (existingStock.getStockName().equals(stock.getStockName())) {
               existingStock.setStockPrice(stock.getStockPrice());
               existingStock.setStockQuantity(existingStock.getStockQuantity() + stock.getStockQuantity());
               stockExists = true;
               break;
           }
       }

       if (!stockExists) {
           player.getPlayerStocks().add(stock);
       }
   }
   
   // 3. 주식 관리: 플레이어 보유 주식 업데이트 (수량이 0이면 제거)
   @Override
   public void updatePlayerStock(Player player, PlayerStock stock) {
       for (int i = 0; i < player.getPlayerStocks().size(); i++) {
           PlayerStock existingStock = player.getPlayerStocks().get(i);
           if (existingStock.getStockName().equals(stock.getStockName())) {
               existingStock.setStockPrice(stock.getStockPrice());
               existingStock.setStockQuantity(stock.getStockQuantity());
               if (existingStock.getStockQuantity() == 0) {
                   player.getPlayerStocks().remove(i);
               }
               break;
           }
       }
   }
   
   // 3. 주식 관리: 인덱스로 플레이어 보유 주식 조회
   @Override
   public PlayerStock findStock(Player player, int index) {
       if (index >= 0 && index < player.getPlayerStocks().size()) {
           return player.getPlayerStocks().get(index);
       }
       return null;
   }
   
   // 3. 주식 관리: 이름으로 플레이어 보유 주식 조회
   @Override
   public PlayerStock findStockByName(Player player, String stockName) {
       for (PlayerStock ps : player.getPlayerStocks()) {
           if (ps.getStockName().equals(stockName)) {
               return ps;
           }
       }
       return null;
   }
   
   // 4. 데이터 변환: 파일 저장용 주식 정보 문자열 생성
   @Override
   public String getPlayerStocksForFile(Player player) {
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < player.getPlayerStocks().size(); i++) {
           if (i > 0) {
               sb.append("|");
           }
           sb.append(player.getPlayerStocks().get(i));
       }
       return sb.toString();
   }
   
   // 4. 데이터 변환: 메뉴 표시용 주식 정보 문자열 생성
   @Override
   public String getPlayerStocksForMenu(Player player) {
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < player.getPlayerStocks().size(); i++) {
           sb.append(i + 1);
           sb.append(". ");
           sb.append(player.getPlayerStocks().get(i).toString());
           sb.append(System.lineSeparator());
       }
       return sb.toString();
   }
}