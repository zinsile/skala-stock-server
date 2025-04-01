/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.04.01
* 
* <클래스 개요>
* 주식과 관련된 API 요청을 처리하는 컨트롤러 클래스
* 
* - RESTful API 엔드포인트 제공
* - JPA 기반으로 수정된 StockService 활용
* - DTO를 사용하여 계층 간 데이터 전송 구조화
* 
* 1. 주식 조회: 전체 주식 목록 및 특정 주식 조회
* 2. 주식 관리: 새 주식 추가, 가격 업데이트
* 3. 메뉴 형식: 주식 목록 메뉴 형식으로 조회
*/

package com.skala.controller;

import com.skala.dto.common.ResponseDto;
import com.skala.dto.stock.StockRequestDto;
import com.skala.dto.stock.StockResponseDto;
import com.skala.model.Stock;
import com.skala.service.StockService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StockController {

   private final StockService stockService;
   
   // 1. 주식 조회: 모든 주식 조회
   @GetMapping
   public ResponseEntity<ResponseDto<List<StockResponseDto>>> getAllStocks() {
       List<Stock> stocks = stockService.getAllStocks();
       List<StockResponseDto> stockDtos = stocks.stream()
           .map(StockResponseDto::from)
           .collect(Collectors.toList());
           
       return ResponseEntity.ok(ResponseDto.success(stockDtos));
   }
   
   // 1. 주식 조회: 이름으로 주식 조회
   @GetMapping("/{name}")
   public ResponseEntity<ResponseDto<StockResponseDto>> getStockByName(@PathVariable String name) {
       Stock stock = stockService.findStockByName(name);
       
       if (stock != null) {
           return ResponseEntity.ok(ResponseDto.success(StockResponseDto.from(stock)));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Stock not found"));
       }
   }
   
   // 2. 주식 관리: 새 주식 추가
   @PostMapping
   public ResponseEntity<ResponseDto<StockResponseDto>> addStock(@RequestBody StockRequestDto.CreateStock request) {
       Stock existingStock = stockService.findStockByName(request.getName());
       
       if (existingStock != null) {
           return ResponseEntity.status(HttpStatus.CONFLICT)
                   .body(ResponseDto.error("Stock already exists"));
       }
       
       Stock newStock = stockService.addStock(request.getName(), request.getPrice());
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(ResponseDto.success("Stock created successfully", StockResponseDto.from(newStock)));
   }
   
   // 2. 주식 관리: 주식 가격 업데이트
   @PutMapping("/{name}")
   public ResponseEntity<ResponseDto<StockResponseDto>> updateStockPrice(
           @PathVariable String name,
           @RequestBody StockRequestDto.UpdatePrice request) {
       
       Stock updatedStock = stockService.updateStockPrice(name, request.getPrice());
       
       if (updatedStock != null) {
           return ResponseEntity.ok(ResponseDto.success("Stock price updated successfully", 
                   StockResponseDto.from(updatedStock)));
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(ResponseDto.error("Stock not found"));
       }
   }
   
   // 3. 메뉴 형식: 메뉴 표시용 주식 목록 조회
   @GetMapping("/menu")
   public ResponseEntity<ResponseDto<String>> getStockListForMenu() {
       String menuText = stockService.getStockListForMenu();
       return ResponseEntity.ok(ResponseDto.success(menuText));
   }
}