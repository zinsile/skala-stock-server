/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.04.01
* 
* <클래스 개요>
* 플레이어와 관련된 API 요청을 처리하는 컨트롤러 클래스
* 
* - RESTful API 엔드포인트 제공
* - JPA 기반으로 수정된 PlayerService와 StockService 활용
* - DTO를 사용하여 계층 간 데이터 전송 구조화
* 
* 1. 플레이어 관리: 조회, 생성, 삭제 기능
* 2. 자금 관리: 플레이어 자금 추가
* 3. 주식 거래: 주식 구매 및 판매
* 4. 주식 정보: 플레이어 보유 주식 조회
*/

package com.skala.controller;

import com.skala.dto.common.ResponseDto;
import com.skala.dto.player.PlayerRequestDto;
import com.skala.dto.player.PlayerResponseDto;
import com.skala.model.Player;
import com.skala.model.PlayerStock;
import com.skala.model.Stock;
import com.skala.service.PlayerService;
import com.skala.service.StockService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PlayerController {

   private final PlayerService playerService;
   private final StockService stockService;
   
   // 1. 플레이어 관리: 모든 플레이어 조회
   @GetMapping
   public ResponseEntity<ResponseDto<List<PlayerResponseDto>>> getAllPlayers() {
       List<Player> players = playerService.getAllPlayers();
       List<PlayerResponseDto> playerDtos = players.stream()
           .map(PlayerResponseDto::from)
           .collect(Collectors.toList());
       
       return ResponseEntity.ok(ResponseDto.success(playerDtos));
   }
   
   // 1. 플레이어 관리: ID로 플레이어 조회
   @GetMapping("/{id}")
   public ResponseEntity<ResponseDto<PlayerResponseDto>> getPlayerById(@PathVariable String id) {
       Player player = playerService.findPlayerByPlayerId(id);
       if (player != null) {
           return ResponseEntity.ok(ResponseDto.success(PlayerResponseDto.from(player)));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
   }
   
   // 1. 플레이어 관리: 새 플레이어 생성
   @PostMapping
   public ResponseEntity<ResponseDto<PlayerResponseDto>> createPlayer(@RequestBody PlayerRequestDto.CreatePlayer request) {
       if (playerService.findPlayerByPlayerId(request.getId()) != null) {
           return ResponseEntity.status(HttpStatus.CONFLICT)
                   .body(ResponseDto.error("Player already exists"));
       }
       
       Player newPlayer = playerService.createPlayer(request.getId());
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(ResponseDto.success("Player created successfully", PlayerResponseDto.from(newPlayer)));
   }
   
   // 1. 플레이어 관리: 플레이어 삭제
   @DeleteMapping("/{id}")
   public ResponseEntity<ResponseDto<Void>> removePlayer(@PathVariable String id) {
       boolean removed = playerService.removePlayer(id);
       if (removed) {
           return ResponseEntity.ok(ResponseDto.success("Player removed successfully"));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
   }
   
   // 2. 자금 관리: 플레이어 자금 추가
   @PostMapping("/{id}/money")
   public ResponseEntity<ResponseDto<PlayerResponseDto.TransactionResult>> addMoney(
           @PathVariable String id,
           @RequestBody PlayerRequestDto.AddMoney request) {
       
       Player playerObj = playerService.findPlayerByPlayerId(id);
       
       if (playerObj != null) {
           playerService.addMoney(playerObj, request.getAmount());
           
           PlayerResponseDto.TransactionResult result = PlayerResponseDto.TransactionResult.builder()
               .message("Money added successfully")
               .success(true)
               .currentMoney(playerObj.getPlayerMoney())
               .build();
               
           return ResponseEntity.ok(ResponseDto.success(result));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
   }
   
   // 3. 주식 거래: 주식 구매
   @PostMapping("/{playerId}/buy")
   public ResponseEntity<ResponseDto<PlayerResponseDto.TransactionResult>> buyStock(
           @PathVariable String playerId,
           @RequestBody PlayerRequestDto.BuyStock request) {
       
       Player playerObj = playerService.findPlayerByPlayerId(playerId);
       if (playerObj == null) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
       
       Stock stockObj = stockService.findStockByName(request.getStockName());
       if (stockObj == null) {
           PlayerResponseDto.TransactionResult result = PlayerResponseDto.TransactionResult.builder()
               .message("Stock not found")
               .success(false)
               .currentMoney(playerObj.getPlayerMoney())
               .build();
               
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(ResponseDto.error("Stock not found", result));
       }
       
       int totalCost = stockObj.getStockPrice() * request.getQuantity();
       if (totalCost <= playerObj.getPlayerMoney()) {
           playerObj.setPlayerMoney(playerObj.getPlayerMoney() - totalCost);
           playerService.addStock(playerObj, stockObj, request.getQuantity());
           playerService.savePlayer(playerObj);
           
           PlayerResponseDto.TransactionResult result = PlayerResponseDto.TransactionResult.builder()
               .message("Stock purchased successfully")
               .success(true)
               .currentMoney(playerObj.getPlayerMoney())
               .build();
               
           return ResponseEntity.ok(ResponseDto.success(result));
       } else {
           PlayerResponseDto.TransactionResult result = PlayerResponseDto.TransactionResult.builder()
               .message("Insufficient funds")
               .success(false)
               .currentMoney(playerObj.getPlayerMoney())
               .build();
               
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
               .body(ResponseDto.error("Insufficient funds", result));
       }
   }
   
   // 3. 주식 거래: 주식 판매
   @PostMapping("/{playerId}/sell")
   public ResponseEntity<ResponseDto<PlayerResponseDto.TransactionResult>> sellStock(
           @PathVariable String playerId,
           @RequestBody PlayerRequestDto.SellStock request) {
       
       Player playerObj = playerService.findPlayerByPlayerId(playerId);
       if (playerObj == null) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
       
       PlayerStock playerStock = playerService.findStockByName(playerObj, request.getStockName());
       if (playerStock == null || playerStock.getStockQuantity() < request.getQuantity()) {
           PlayerResponseDto.TransactionResult result = PlayerResponseDto.TransactionResult.builder()
               .message("Insufficient stock quantity")
               .success(false)
               .currentMoney(playerObj.getPlayerMoney())
               .build();
               
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
               .body(ResponseDto.error("Insufficient stock quantity", result));
       }
       
       Stock baseStock = stockService.findStockByName(request.getStockName());
       int earnedMoney = baseStock.getStockPrice() * request.getQuantity();
       playerObj.setPlayerMoney(playerObj.getPlayerMoney() + earnedMoney);
       playerStock.setStockQuantity(playerStock.getStockQuantity() - request.getQuantity());
       playerService.updatePlayerStock(playerObj, playerStock);
       playerService.savePlayer(playerObj);
       
       PlayerResponseDto.TransactionResult result = PlayerResponseDto.TransactionResult.builder()
           .message("Stock sold successfully")
           .success(true)
           .currentMoney(playerObj.getPlayerMoney())
           .build();
           
       return ResponseEntity.ok(ResponseDto.success(result));
   }
   
   // 4. 주식 정보: 플레이어 보유 주식 조회
   @GetMapping("/{playerId}/stocks")
   public ResponseEntity<ResponseDto<List<PlayerResponseDto.PlayerStockInfo>>> getPlayerStocks(@PathVariable String playerId) {
       Player player = playerService.findPlayerByPlayerId(playerId);
       
       if (player != null) {
           List<PlayerResponseDto.PlayerStockInfo> stockInfos = player.getPlayerStocks().stream()
               .map(PlayerResponseDto.PlayerStockInfo::from)
               .collect(Collectors.toList());
               
           return ResponseEntity.ok(ResponseDto.success(stockInfos));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
   }
   
   // 4. 주식 정보: 메뉴 형식으로 플레이어 보유 주식 조회
   @GetMapping("/{playerId}/stocks/menu")
   public ResponseEntity<ResponseDto<String>> getPlayerStocksMenu(@PathVariable String playerId) {
       Player player = playerService.findPlayerByPlayerId(playerId);
       
       if (player != null) {
           String menuFormat = playerService.getPlayerStocksForMenu(player);
           return ResponseEntity.ok(ResponseDto.success(menuFormat));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Player not found"));
       }
   }
}