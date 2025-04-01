/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어와 관련된 API 요청을 처리하는 컨트롤러 클래스
* 
* - RESTful API 엔드포인트 제공
* - JPA 기반으로 수정된 PlayerService와 StockService 활용
* 
* 1. 플레이어 관리: 조회, 생성, 삭제 기능
* 2. 자금 관리: 플레이어 자금 추가
* 3. 주식 거래: 주식 구매 및 판매
* 4. 주식 정보: 플레이어 보유 주식 조회
*/

package com.skala.controller;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import com.skala.model.Stock;
import com.skala.service.PlayerService;
import com.skala.service.StockService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PlayerController {

   private final PlayerService playerService;
   private final StockService stockService;
   
//    @Autowired
//    public PlayerController(PlayerService playerService, StockService stockService) {
//        this.playerService = playerService;
//        this.stockService = stockService;
//    }
   
   // 1. 플레이어 관리: 모든 플레이어 조회
   @GetMapping
   public List<Player> getAllPlayers() {
       return playerService.getAllPlayers();
   }
   
   // 1. 플레이어 관리: ID로 플레이어 조회
   @GetMapping("/{id}")
   public ResponseEntity<Player> getPlayerById(@PathVariable String id) {
       Player player = playerService.findPlayerByPlayerId(id);
       if (player != null) {
           return new ResponseEntity<>(player, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }
   
   // 1. 플레이어 관리: 새 플레이어 생성
   @PostMapping
   public ResponseEntity<Player> createPlayer(@RequestParam String id) {
       if (playerService.findPlayerByPlayerId(id) != null) {
           return new ResponseEntity<>(HttpStatus.CONFLICT); // 이미 존재하는 플레이어
       }
       
       Player newPlayer = playerService.createPlayer(id);
       return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
   }
   
   // 1. 플레이어 관리: 플레이어 삭제
   @DeleteMapping("/{id}")
   public ResponseEntity<String> removePlayer(@PathVariable String id) {
       boolean removed = playerService.removePlayer(id);
       if (removed) {
           return new ResponseEntity<>("Player removed successfully", HttpStatus.OK);
       } else {
           return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
       }
   }
   
   // 2. 자금 관리: 플레이어 자금 추가
   @PostMapping("/{id}/money")
   public ResponseEntity<String> addMoney(
           @PathVariable String id,
           @RequestParam int amount) {
       
       Player playerObj = playerService.findPlayerByPlayerId(id);
       
       if (playerObj != null) {
           playerService.addMoney(playerObj, amount);
           return new ResponseEntity<>("Money added successfully", HttpStatus.OK);
       } else {
           return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
       }
   }
   
   // 3. 주식 거래: 주식 구매
   @PostMapping("/{playerId}/buy")
   public ResponseEntity<String> buyStock(
           @PathVariable String playerId,
           @RequestParam String stockName,
           @RequestParam int quantity) {
       
       Player playerObj = playerService.findPlayerByPlayerId(playerId);
       if (playerObj == null) {
           return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
       }
       
       Stock stockObj = stockService.findStockByName(stockName);
       if (stockObj == null) {
           return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
       }
       
       int totalCost = stockObj.getStockPrice() * quantity;
       if (totalCost <= playerObj.getPlayerMoney()) {
           playerObj.setPlayerMoney(playerObj.getPlayerMoney() - totalCost);
           playerService.addStock(playerObj, stockObj, quantity);
           playerService.savePlayer(playerObj);
           return new ResponseEntity<>("Success", HttpStatus.OK);
       } else {
           return new ResponseEntity<>("Insufficient funds", HttpStatus.BAD_REQUEST);
       }
   }
   
   // 3. 주식 거래: 주식 판매
   @PostMapping("/{playerId}/sell")
   public ResponseEntity<String> sellStock(
           @PathVariable String playerId,
           @RequestParam String stockName,
           @RequestParam int quantity) {
       
       Player playerObj = playerService.findPlayerByPlayerId(playerId);
       if (playerObj == null) {
           return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
       }
       
       PlayerStock playerStock = playerService.findStockByName(playerObj, stockName);
       if (playerStock == null || playerStock.getStockQuantity() < quantity) {
           return new ResponseEntity<>("Insufficient stock quantity", HttpStatus.BAD_REQUEST);
       }
       
       Stock baseStock = stockService.findStockByName(stockName);
       int earnedMoney = baseStock.getStockPrice() * quantity;
       playerObj.setPlayerMoney(playerObj.getPlayerMoney() + earnedMoney);
       playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
       playerService.updatePlayerStock(playerObj, playerStock);
       playerService.savePlayer(playerObj);
       
       return new ResponseEntity<>("Success", HttpStatus.OK);
   }
   
   // 4. 주식 정보: 플레이어 보유 주식 조회
   @GetMapping("/{playerId}/stocks")
   public ResponseEntity<List<PlayerStock>> getPlayerStocks(@PathVariable String playerId) {
       Player player = playerService.findPlayerByPlayerId(playerId);
       
       if (player != null) {
           return new ResponseEntity<>(player.getPlayerStocks(), HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }
   
   // 4. 주식 정보: 메뉴 형식으로 플레이어 보유 주식 조회
   @GetMapping("/{playerId}/stocks/menu")
   public ResponseEntity<String> getPlayerStocksMenu(@PathVariable String playerId) {
       Player player = playerService.findPlayerByPlayerId(playerId);
       
       if (player != null) {
           String menuFormat = playerService.getPlayerStocksForMenu(player);
           return new ResponseEntity<>(menuFormat, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }
}