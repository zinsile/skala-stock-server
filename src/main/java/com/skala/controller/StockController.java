/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 주식과 관련된 API 요청을 처리하는 컨트롤러 클래스
* 
* - RESTful API 엔드포인트 제공
* - StockService 활용하여 주식 관련 비즈니스 로직 처리
* 
* 1. 주식 조회: 전체 주식 목록 및 특정 주식 조회
* 2. 주식 관리: 새 주식 추가, 가격 업데이트
* 3. 메뉴 형식: 주식 목록 메뉴 형식으로 조회
*/

package com.skala.controller;

import com.skala.model.Stock;
import com.skala.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class StockController {

   private final StockService stockService;
   
   @Autowired
   public StockController(StockService stockService) {
       this.stockService = stockService;
   }
   
   // 1. 주식 조회: 모든 주식 조회
   @GetMapping
   public List<Stock> getAllStocks() {
       stockService.loadStock();
       return stockService.getStock();
   }
   
   // 1. 주식 조회: 이름으로 주식 조회
   @GetMapping("/{name}")
   public ResponseEntity<Stock> getStockByName(@PathVariable String name) {
       stockService.loadStock();
       Stock stock = stockService.findStockByName(name);
       
       if (stock != null) {
           return new ResponseEntity<>(stock, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }
   
   // 2. 주식 관리: 새 주식 추가
   @PostMapping
   public ResponseEntity<Stock> addStock(
           @RequestParam String name,
           @RequestParam int price) {
       
       stockService.loadStock();
       Stock existingStock = stockService.findStockByName(name);
       
       if (existingStock != null) {
           return new ResponseEntity<>(HttpStatus.CONFLICT); // 이미 존재하는 주식
       }
       
       stockService.addStock(name, price);
       Stock newStock = stockService.findStockByName(name);
       return new ResponseEntity<>(newStock, HttpStatus.CREATED);
   }
   
   // 2. 주식 관리: 주식 가격 업데이트
   @PutMapping("/{name}")
   public ResponseEntity<Stock> updateStockPrice(
           @PathVariable String name,
           @RequestParam int price) {
       
       stockService.loadStock();
       Stock stock = stockService.findStockByName(name);
       
       if (stock == null) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       
       // 주의: 임시 구현 - 실제 구현에서는 저장소에서 직접 업데이트 필요
       List<Stock> stocks = stockService.getStock();
       for (Stock s : stocks) {
           if (s.getStockName().equals(name)) {
               s.setStockPrice(price);
               break;
           }
       }
       
       stockService.loadStock();
       Stock updatedStock = stockService.findStockByName(name);
       return new ResponseEntity<>(updatedStock, HttpStatus.OK);
   }
   
   // 3. 메뉴 형식: 메뉴 표시용 주식 목록 조회
   @GetMapping("/menu")
   public ResponseEntity<String> getStockListForMenu() {
       stockService.loadStock();
       String menuText = stockService.getStockListForMenu();
       return new ResponseEntity<>(menuText, HttpStatus.OK);
   }
}