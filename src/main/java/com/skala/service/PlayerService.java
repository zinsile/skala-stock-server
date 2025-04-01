/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어 관련 비즈니스 로직을 처리하는 서비스 클래스
* 
* - JPA 리포지토리를 통해 데이터 접근
* - 파일 기반 동작에서 데이터베이스 기반 동작으로 변경
* - PlayerServiceInterface 구현
* 
* 1. 플레이어 관리: 조회, 생성, 저장, 삭제
* 2. 자금 관리: 플레이어 자금 추가
* 3. 주식 관리: 추가, 업데이트, 조회
* 4. 데이터 변환: 주식 정보를 다양한 형식으로 변환
*/

package com.skala.service;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import com.skala.model.Stock;
import com.skala.repository.PlayerRepository;
import com.skala.repository.PlayerStockRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerService implements PlayerServiceInterface {
   
   private final PlayerRepository playerRepository;
   private final PlayerStockRepository playerStockRepository;
   
//    @Autowired
//    public PlayerService(PlayerRepository playerRepository, PlayerStockRepository playerStockRepository) {
//        this.playerRepository = playerRepository;
//        this.playerStockRepository = playerStockRepository;
//    }

   // 1. 플레이어 관리: 전체 플레이어 목록 반환
   @Override
   public List<Player> getAllPlayers() {
       return playerRepository.findAll();
   }

   // 1. 플레이어 관리: ID로 플레이어 조회
   @Override
   public Player findPlayerByPlayerId(String id) {
       return playerRepository.findByPlayerId(id).orElse(null);
   }
   
   // 1. 플레이어 관리: 새 플레이어 생성
   @Override
   public Player createPlayer(String id) {
       Player player = new Player(id);
       return playerRepository.save(player);
   }

   // 1. 플레이어 관리: 플레이어 저장
   @Override
   public Player savePlayer(Player player) {
       return playerRepository.save(player);
   }

   // 1. 플레이어 관리: 플레이어 삭제
   @Override
   public boolean removePlayer(String id) {
       Optional<Player> player = playerRepository.findByPlayerId(id);
       if (player.isPresent()) {
           playerRepository.delete(player.get());
           return true;
       }
       return false;
   }
   
   // 2. 자금 관리: 플레이어 자금 추가
   @Override
   public void addMoney(Player player, int amount) {
       if (amount > 0) {
           player.setPlayerMoney(player.getPlayerMoney() + amount);
           playerRepository.save(player);
       }
   }
   
   // 3. 주식 관리: 플레이어에게 주식 추가 (중복 시 수량만 증가)
   @Override
   public PlayerStock addStock(Player player, Stock stock, int quantity) {
       Optional<PlayerStock> existingStockOpt = playerStockRepository.findByPlayerAndStockName(player, stock.getStockName());
       
       if (existingStockOpt.isPresent()) {
           // 이미 보유한 주식이 있으면 수량만 증가
           PlayerStock existingStock = existingStockOpt.get();
           existingStock.setStockQuantity(existingStock.getStockQuantity() + quantity);
           return playerStockRepository.save(existingStock);
       } else {
           // 새로운 주식 추가
           PlayerStock newStock = new PlayerStock(stock, quantity);
           newStock.setPlayer(player);
           return playerStockRepository.save(newStock);
       }
   }
   
   // 3. 주식 관리: 플레이어 보유 주식 업데이트 (수량이 0이면 제거)
   @Override
   public void updatePlayerStock(Player player, PlayerStock stock) {
       if (stock.getStockQuantity() <= 0) {
           // 수량이 0 이하면 주식 제거
           playerStockRepository.delete(stock);
       } else {
           // 수량 업데이트
           playerStockRepository.save(stock);
       }
   }
   
   // 3. 주식 관리: 이름으로 플레이어 보유 주식 조회
   @Override
   public PlayerStock findStockByName(Player player, String stockName) {
       return playerStockRepository.findByPlayerAndStockName(player, stockName).orElse(null);
   }
   
   // 4. 데이터 변환: 메뉴 표시용 주식 정보 문자열 생성
   @Override
   public String getPlayerStocksForMenu(Player player) {
       StringBuilder sb = new StringBuilder();
       List<PlayerStock> stocks = playerStockRepository.findByPlayer(player);
       
       for (int i = 0; i < stocks.size(); i++) {
           sb.append(i + 1);
           sb.append(". ");
           sb.append(stocks.get(i).toString());
           sb.append(System.lineSeparator());
       }
       return sb.toString();
   }
}